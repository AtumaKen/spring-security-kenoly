package com.kenoly.udemy;

import com.kenoly.udemy.io.entity.UserEntity;
import com.kenoly.udemy.io.repository.UserRepository;
import com.kenoly.udemy.service.impl.UserServiceImpl;
import com.kenoly.udemy.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetUser() {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId("Ider");
		userEntity.setFirstName("ken");
		userEntity.setLastName("Ben");
		userEntity.setEmail("test@test.com");
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("lokk");
		assertNotNull(userDto);
		assertEquals("Ider", userDto.getUserId());
	}

}
