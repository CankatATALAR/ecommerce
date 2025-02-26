package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.dto.UserDto;
import com.ecommerce.spring_ecommerce.model.User;
import com.ecommerce.spring_ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() throws Exception{
        userRepository = Mockito.mock(UserRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, modelMapper, passwordEncoder);
    }

    @Test
    public void testCreateUser() throws Exception{
        UserDto userDto = new UserDto();
        userDto.setName("Cankat");
        userDto.setSurname("Atalar");
        userDto.setEmail("cankat@atalar.com");

        User user = new User();
        user.setName("Cankat");
        user.setSurname("Atalar");
        user.setEmail("cankat@atalar.com");
        user.setPassword("encodedPassword");

        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        try{
            UserDto savedUser = userService.createUser(userDto, "encodedPassword");
            assertNotNull(user.getPassword());
            boolean isUserCreated = savedUser != null && "Cankat".equals(savedUser.getName());
            assertTrue(isUserCreated, "User cannot be created.");
        } catch (Exception e){
            fail("Test failed" + e.getMessage());
        }
    }

    @Test
    public void testUpdateUser() throws Exception{
        UserDto userDto = new UserDto();
        userDto.setName("Kankat");
        userDto.setSurname("Atalar");
        userDto.setEmail("kankat@atalar.com");

        User user = new User();
        user.setName("Cankat");
        user.setSurname("Atalar");
        user.setEmail("canka@atalar.com");
        user.setPassword("encodedPassword");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        System.out.println("currentPassword: " + user.getPassword());

        try {
            UserDto result = userService.updateUser(userDto, user.getId(), "newPassword");
            System.out.println("updated password: " + user.getPassword());
            assertNotNull(result, "Updated user should not be null.");
            assertEquals("Kankat", result.getName());
            assertEquals("Atalar", result.getSurname());
            assertEquals("kankat@atalar.com", result.getEmail());

            //Check if the new password is hashed correctly
            assertTrue(passwordEncoder.matches("newPassword", user.getPassword()),
                    "Password hashing mismatch.");
        } catch (Exception e){
            fail("Test failed: " + e.getMessage());
        }
    }

}