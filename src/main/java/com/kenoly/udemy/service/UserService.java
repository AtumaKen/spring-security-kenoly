package com.kenoly.udemy.service;

import com.kenoly.udemy.shared.dto.UserDto;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String email);
	UserDto getUserByUserId(String id);
	UserDto updateUser(String userId, UserDto userDto);
	void deleteUser(String userId);
	List<UserDto> getUsers(int page, int limit);
}
