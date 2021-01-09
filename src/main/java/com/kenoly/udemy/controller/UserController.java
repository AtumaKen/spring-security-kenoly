package com.kenoly.udemy.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kenoly.udemy.model.request.UserDetailsRequestModel;
import com.kenoly.udemy.model.response.AdressesRest;
import com.kenoly.udemy.model.response.ErrorMessages;
import com.kenoly.udemy.model.response.OperationStatusModel;
import com.kenoly.udemy.model.response.ResponseOperationStatus;
import com.kenoly.udemy.model.response.UserRest;
import com.kenoly.udemy.service.AddressService;
import com.kenoly.udemy.service.UserService;
import com.kenoly.udemy.shared.dto.AddressDto;
import com.kenoly.udemy.shared.dto.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;

	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable String id) {
		UserDto userDto = userService.getUserByUserId(id);
		if (userDto == null)
			throw new UsernameNotFoundException("User with ID: " + id + " not found");
		ModelMapper mapper = new ModelMapper();
		return mapper.map(userDto, UserRest.class);
	}

	@PostMapping
	public UserRest createUSer(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		UserRest returnValue = new UserRest();
		if (userDetails.getLastName().isEmpty() || userDetails.getFirstName().isEmpty()
				|| userDetails.getAddress() == null || userDetails.getEmail().isEmpty()
				|| userDetails.getPassword().isEmpty())
			throw new Exception(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto createdUser = userService.createUser(userDto);
		returnValue = modelMapper.map(createdUser, UserRest.class);
		return returnValue;
	}

	@PutMapping(path = "/{id}")
	public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails, @PathVariable String id) {
		UserRest returnValue = new UserRest();
//         if(userDetails.getFirstName().isEmpty()) throw new Exception(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationNames.DELETE.name());
		userService.deleteUser(id);
		returnValue.setOperationStatus(ResponseOperationStatus.SUCCESS.name());
		return returnValue;
	}

	@GetMapping
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		return returnValue;
	}
	
	@GetMapping(path = "/{id}/address")
	public List<AdressesRest> getUserAddresses(@PathVariable String id) {
		List<AdressesRest> returnValue = new ArrayList<>();
		List<AddressDto> addressDto = addressService.getAddress(id);
		
		if(addressDto != null && !addressDto.isEmpty()) {
		Type listType = new TypeToken<List<AdressesRest>>() {}.getType();
		returnValue = new ModelMapper().map(addressDto, listType);
		}

		return returnValue;
	}
	
//	@GetMapping(path = "/{userId}/address/{addressId}")
//	public AdressesRest getUserAddress(@PathVariable String addressId) {
//		AddressDto addressDto = addressService.get
//	}

}
