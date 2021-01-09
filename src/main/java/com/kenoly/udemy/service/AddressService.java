package com.kenoly.udemy.service;

import java.util.List;

import com.kenoly.udemy.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto> getAddress(String userId);
}
