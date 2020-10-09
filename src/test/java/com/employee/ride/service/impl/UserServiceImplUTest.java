package com.employee.ride.service.impl;

import com.employee.ride.entity.User;
import com.employee.ride.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.AssertionErrors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class UserServiceImplUTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_return_the_list_of_employee() {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(
                User.builder()
                        .id(1L)
                        .firstname("firstname1")
                        .build(),
                User.builder()
                        .id(2L)
                        .firstname("firstname2")
                        .build())
        );

        List<User> listOfUser = userService.getListOfUser();

        AssertionErrors.assertEquals("List of user not empty",2,listOfUser.size());
    }

    @Test
    void should_return_an_empty_list_of_employee() {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
        List<User> listOfUser = userService.getListOfUser();
        AssertionErrors.assertEquals("An empty list of user",0,listOfUser.size());
    }

}