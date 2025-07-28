package com.gooderreads.gooder_reads.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gooderreads.gooder_reads.dto.UserDTO;
import com.gooderreads.gooder_reads.entity.User;

@Component
public class UserMapper {
    
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }
}
