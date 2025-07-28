package com.gooderreads.gooder_reads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooderreads.gooder_reads.repository.UserRepository;
import com.gooderreads.gooder_reads.dto.UserDTO;
import com.gooderreads.gooder_reads.entity.User;
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
            .orElseThrow(() -> new RuntimeException("Book not found."));
        return userMapper.convertToDTO(user);
    }
    
}
