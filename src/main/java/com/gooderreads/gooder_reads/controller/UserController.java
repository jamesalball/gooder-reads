package com.gooderreads.gooder_reads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.gooderreads.gooder_reads.entity.User;

import com.gooderreads.gooder_reads.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/user")
    public User getMethodName(@RequestParam Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }
    
    
}
