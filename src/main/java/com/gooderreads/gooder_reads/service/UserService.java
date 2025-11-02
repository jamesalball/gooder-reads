package com.gooderreads.gooder_reads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooderreads.gooder_reads.repository.UserRepository;
import com.gooderreads.gooder_reads.dto.UserDTO;
import com.gooderreads.gooder_reads.entity.User;
import com.gooderreads.gooder_reads.exception.ResourceNotFoundException;
import com.gooderreads.gooder_reads.mapper.UserMapper;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    @Transactional
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
            .map(user -> userMapper.convertToDTO(user))
            .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.convertToDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserDTO newUser) {

        User savedUser = userMapper.convertToEntity(newUser);
        savedUser = userRepository.save(savedUser);

        UserDTO savedUserDTO = userMapper.convertToDTO(savedUser);

        return savedUserDTO;

    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO updatedUser) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setEmail(updatedUser.getEmail());
        user.setDisplayName(updatedUser.getDisplayName());

        User updatedSavedUser = userRepository.save(user);
        UserDTO updatedUserDTO = userMapper.convertToDTO(updatedSavedUser);

        return updatedUserDTO;

    }

    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);

    }
    
}
