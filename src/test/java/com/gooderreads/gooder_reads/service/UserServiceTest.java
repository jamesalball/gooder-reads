package com.gooderreads.gooder_reads.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gooderreads.gooder_reads.dto.UserDTO;
import com.gooderreads.gooder_reads.entity.User;
import com.gooderreads.gooder_reads.entity.Review;
import com.gooderreads.gooder_reads.exception.ResourceNotFoundException;
import com.gooderreads.gooder_reads.mapper.UserMapper;
import com.gooderreads.gooder_reads.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    //Long variable to be used as Id
    private Long recordId = 1L;

    //User, Optional<User>, and UserDTO variables to be used in the test methods
    private User expectedUser;
    private Optional<User> expectedUserOptional;
    private UserDTO expectedUserDTO;

    @BeforeEach
    void setUp() {

        expectedUser = new User(recordId, "test@tester.com", "Test User", new ArrayList<Review>());
        expectedUserOptional = Optional.of(expectedUser);
        expectedUserDTO = new UserDTO(recordId, "test@tester.com", "Test User");
        
    }

    //Test getting a User by its Id successfully
    @Test
    void testGetUserById_Success() {

        //Mock userRepository and userMapper behavior
        when(userRepository.findById(recordId)).thenReturn(expectedUserOptional);
        when(userMapper.convertToDTO(expectedUser)).thenReturn(expectedUserDTO);

        //Call the method being tested
        UserDTO actualUserDTO = userService.getUserById(recordId);

        //Assert a userDTO was returned
        assertNotNull(actualUserDTO);

        //Assert the userDTO data matches what's expected
        assertEquals(expectedUserDTO.getId(), actualUserDTO.getId());
        assertEquals(expectedUser.getEmail(), actualUserDTO.getEmail());

        //Verify the method being tested was called
        verify(userRepository, times(1)).findById(recordId);

    }

    //Test attempting to get a User by its Id unsusccessfully. Applicable for getting, updating, or deleting a nonexistent user
    @Test
    void testGetUserById_NotFound() {

        //Mock userRepository behavior
        when(userRepository.findById(recordId)).thenReturn(Optional.empty());

        //Trying to find a User that doesn't exist should throw a ResourceNotFoundException
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(recordId));

        //Assert the expected exception was thrown
        assertEquals("User not found", thrown.getMessage());

        //Verify the method being tested was called
        verify(userRepository, times(1)).findById(recordId);

    }

    //Test listing all User records
    @Test
    void testGetAllUsers() {

        //Add test variables to a List
        List<User> expectedUserList = new ArrayList<User>();

        Long secondRecordId = 2L;
        User expectedUserTwo = new User(secondRecordId, "test2@tester.com", "Second Test User", new ArrayList<Review>());
        UserDTO expectedUserTwoDTO = new UserDTO(secondRecordId, "test2@tester.com", "Second Test User");

        expectedUserList.add(expectedUser);
        expectedUserList.add(expectedUserTwo);

        //Mock userRepository and userMapper behavior
        when(userRepository.findAll()).thenReturn(expectedUserList);
        when(userMapper.convertToDTO(expectedUser)).thenReturn(expectedUserDTO);
        when(userMapper.convertToDTO(expectedUserTwo)).thenReturn(expectedUserTwoDTO);

        //Call the method being tested
        List<UserDTO> actualUserList = userService.getAllUsers();

        //Assert a List<UserDTO> was returned
        assertNotNull(actualUserList);

        //Assert the actual UserDTO data matches the expected data
        assertEquals(expectedUserList.get(0).getId(), actualUserList.get(0).getId());
        assertEquals(expectedUserList.get(0).getEmail(), actualUserList.get(0).getEmail());

        assertEquals(expectedUserList.get(1).getId(), actualUserList.get(1).getId());
        assertEquals(expectedUserList.get(1).getEmail(), actualUserList.get(1).getEmail());

        //Verify the method being tested was called
        verify(userRepository, times(1)).findAll();

    }

    //Test creating a User record
    @Test
    void testCreateUser() {


        UserDTO createUserDTO = new UserDTO();
        createUserDTO.setEmail("newUser@testing.com");
        createUserDTO.setDisplayName("New User");

        //Mock userRepository and userMapper behavior
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        when(userMapper.convertToEntity(any(UserDTO.class))).thenReturn(expectedUser);
        when(userMapper.convertToDTO(any(User.class))).thenReturn(expectedUserDTO);

        //Call the method being tested
        UserDTO actualUserDTO = userService.createUser(createUserDTO);

        //Assert the actual UserDTO data matches the expected UserDTO data
        assertEquals(expectedUserDTO.getId(), actualUserDTO.getId());
        assertEquals(expectedUserDTO.getEmail(), actualUserDTO.getEmail());

        //Verify the method being tested was called
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void testUpdateUser() {

        //Set updated data
        User updatedUser = new User(recordId, "updatedEmail@testing.com", "Updated Display Name", new ArrayList<Review>());
        
        UserDTO expectedUpdatedUserDTO = new UserDTO();
        expectedUpdatedUserDTO.setEmail("updatedEmail@testing.com");
        expectedUpdatedUserDTO.setDisplayName("Updated Display Name");
        expectedUpdatedUserDTO.setId(recordId);

        //Mock userRepository behavior
        when(userRepository.findById(recordId)).thenReturn(expectedUserOptional);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userMapper.convertToDTO(updatedUser)).thenReturn(expectedUpdatedUserDTO);

        //Call the method being tested
        UserDTO actualUpdatedUserDTO = userService.updateUser(recordId, expectedUpdatedUserDTO);

        //Assert the expected data matches the actual data
        assertEquals(expectedUpdatedUserDTO.getId(), actualUpdatedUserDTO.getId());
        assertEquals(expectedUpdatedUserDTO.getEmail(), actualUpdatedUserDTO.getEmail());

        //Verify the method being tested was called
        verify(userRepository, times(1)).save(any(User.class));

    }

    //Test deleting a User record
    @Test
    void testDeleteUser() {

        //Mock userRepository behavior
        when(userRepository.findById(recordId)).thenReturn(expectedUserOptional);
        
        //Call the method being tested
        userService.deleteUser(recordId);

        //Verify the method being tested was called
        verify(userRepository, times(1)).delete(expectedUser);
    }

}
