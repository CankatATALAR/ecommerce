package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.dto.UserDto;
import com.ecommerce.spring_ecommerce.model.User;
import com.ecommerce.spring_ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserDto userDto, String password){
        //PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(encodedPassword);

        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public List<UserDto> getUsers(){
        List<User> users = userRepository.findAll();
        List<UserDto> dtos = users.stream().map(user -> modelMapper.map(user, UserDto.class))
                                           .collect(Collectors.toList());

        return  dtos;
    }

    public UserDto getUserById(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return modelMapper.map(user.get(), UserDto.class);
        }
        throw new RuntimeException("User Cannot Found");
    }

    public UserDto updateUser(UserDto userDto, String id, String password){
        //PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setName(userDto.getName());
            user.get().setSurname(userDto.getSurname());
            user.get().setEmail(userDto.getEmail());
            user.get().setPassword(encodedPassword);

            return modelMapper.map(userRepository.save(user.get()), UserDto.class);
        }
        return null;
    }

    public boolean deleteUser(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
