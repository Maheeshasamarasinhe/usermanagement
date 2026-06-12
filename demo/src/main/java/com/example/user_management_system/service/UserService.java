package com.example.user_management_system.service;

import com.example.user_management_system.dto.UserRequestDto;
import com.example.user_management_system.dto.UserResponseDto;
import com.example.user_management_system.entity.User;
import com.example.user_management_system.repository.UserRepository;
import com.example.user_management_system.specification.Userspecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponseDto createUser(UserRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Mail already in use");
        }

        User user = modelMapper.map(dto, User.class);
        user.setStatus(true);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));



        // Update the fields
        existingUser.setUsername(dto.getUsername());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPassword(dto.getPassword());
        existingUser.setRole(dto.getRole());
        existingUser.setTelephone(dto.getTelephone());

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setStatus(false);
        userRepository.save(user);
    }

    public List<UserResponseDto> searchUsers(Map<String, String> searchCriteria) {
        Userspecification specification = new Userspecification(searchCriteria);
        return userRepository.findAll(specification).stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }
}