package com.employee.ride.resource;

import com.employee.ride.common.constant.UserConstant;
import com.employee.ride.common.constant.utils.JsonUtils;
import com.employee.ride.entity.User;
import com.employee.ride.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    @Test
    void should_return_a_list_of_user() throws Exception {
        //When
        Mockito.when(userService.getListOfUser()).thenReturn(Arrays.asList(
                User.builder()
                        .id(1L)
                        .firstname("firstname1")
                        .build(),
                User.builder()
                        .id(2L)
                        .firstname("firstname2")
                        .build())
        );

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(UserConstant.USER_URL_BASE + UserConstant.USERS))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<User> userList = JsonUtils.deserializeStringToList(content, User.class);

        //Then
        AssertionErrors.assertEquals("Two users found ", 2, userList.size());

    }

    @Test
    void should_return_an_empty_list_of_user() throws Exception {
        //When
        Mockito.when(userService.getListOfUser()).thenReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(UserConstant.USER_URL_BASE + UserConstant.USERS))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<User> userList = JsonUtils.deserializeStringToList(content, User.class);

        //Then
        AssertionErrors.assertEquals("No user found ", 0, userList.size());

    }

}