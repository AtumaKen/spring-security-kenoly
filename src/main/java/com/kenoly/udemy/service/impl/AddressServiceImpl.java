package com.kenoly.udemy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kenoly.udemy.io.entity.AddressEntity;
import com.kenoly.udemy.io.entity.UserEntity;
import com.kenoly.udemy.io.repository.AddressRepository;
import com.kenoly.udemy.io.repository.UserRepository;
import com.kenoly.udemy.service.AddressService;
import com.kenoly.udemy.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;

	@Override
	public List<AddressDto> getAddress(String userId) {
		List<AddressDto> returnValue = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) return returnValue;
		
		Iterable<AddressEntity> address = addressRepository.findAllByUserDetails(userEntity);
		for(AddressEntity addressEntity : address) {
			returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
		}
		return returnValue;
	}

}
