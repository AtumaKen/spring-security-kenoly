package com.kenoly.udemy.service.impl;

import com.kenoly.udemy.io.entity.UserEntity;
import com.kenoly.udemy.io.repository.UserRepository;
import com.kenoly.udemy.model.response.ErrorMessages;
import com.kenoly.udemy.shared.Utils;
import com.kenoly.udemy.shared.dto.AddressDto;
import com.kenoly.udemy.shared.dto.UserDto;
import com.kenoly.udemy.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils userUtils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new RuntimeException("Record already exists");
        
        for(int i = 0; i<user.getAddress().size(); i++) {
        	AddressDto address = user.getAddress().get(i);
        	address.setUserDetails(user);
        	address.setAddressId(userUtils.generateAddressId(30));
        	user.getAddress().set(i, address);
        }
     
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(userUtils.generateKey(30));
        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDto.class);
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByEmail(email);
        if(entity == null) throw new UsernameNotFoundException(email);
        return new User(entity.getEmail(), entity.getEncryptedPassword(), new ArrayList<>());
    }

	@Override
	public UserDto getUserByUserId(String id) {
		UserEntity userEntity = userRepository.findByUserId(id);
		if(userEntity == null) throw new UsernameNotFoundException(id);		
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		UserEntity updatedValues = userRepository.save(userEntity);		
		
		BeanUtils.copyProperties(updatedValues, returnValue);
		
		
		return returnValue;
	}

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        userRepository.delete(userEntity);
    }

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();
		if(page>0) page -= 1;
		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = userPage.getContent();
		for(UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		return returnValue;
	}
}
