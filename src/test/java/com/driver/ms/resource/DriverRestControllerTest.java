package com.driver.ms.resource;

import com.driver.ms.common.constant.DriverConstant;
import com.driver.ms.common.utils.JsonUtils;
import com.driver.ms.entity.Address;
import com.driver.ms.entity.Driver;
import com.driver.ms.service.DriverService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.driver.ms.common.constant.CommonConstant.DRIVER_URL_BASE;
import static com.driver.ms.entity.Driver.builder;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DriverRestControllerTest {

    @InjectMocks
    private DriverRestController driverRestController;

    @Mock
    private DriverService driverService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(driverRestController).build();
    }

    @Test
    void should_return_a_list_of_user() throws Exception {
        //When
        when(driverService.getListOfDriver()).thenReturn(Arrays.asList(
                builder()
                        .id(1L)
                        .firstname("firstname1")
                        .build(),
                builder()
                        .id(2L)
                        .firstname("firstname2")
                        .build())
        );

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(DRIVER_URL_BASE + DriverConstant.DRIVERS))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<Driver> driverList = JsonUtils.deserializeStringToList(content, Driver.class);

        //Then
        AssertionErrors.assertEquals("Two users found ", 2, driverList.size());

        verify(driverService, times(1)).getListOfDriver();

    }

    @Test
    void should_return_an_empty_list_of_user() throws Exception {
        //When
        when(driverService.getListOfDriver()).thenReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(DRIVER_URL_BASE + DriverConstant.DRIVERS))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<Driver> driverList = JsonUtils.deserializeStringToList(content, Driver.class);

        //Then
        AssertionErrors.assertEquals("No user found ", 0, driverList.size());

        verify(driverService, times(1)).getListOfDriver();

    }

    @Test
    void should_apply_advanced_filter_and_return_the_right_values() throws Exception {
        List<Driver> driversFound = Arrays.asList(Driver.builder()
                        .id(1L)
                        .firstname("firstname1")
                        .address(Address.builder().phone("062323236").build())
                        .build(),
                Driver.builder()
                        .id(2L)
                        .firstname("firstname2")
                        .address(Address.builder().phone("062323236").build())
                        .build()
        );

        when(driverService.findByFirstName(anyString())).thenReturn(driversFound);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(DRIVER_URL_BASE + DriverConstant.DRIVER_ADVANCED_SEARCH)
                .accept(MediaType.APPLICATION_JSON)
                .param("firstname", "firstname")
                .param("null", "null")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Driver> drivers = JsonUtils.deserializeStringToList(contentAsString, Driver.class);

        Assertions.assertAll("Check condition", () -> Assertions.assertEquals(2, drivers.size()));

        verify(driverService, times(1)).findByFirstName(anyString());

    }

    @Test
    void should_apply_advanced_filter_and_return_an_empty_list() throws Exception {
        List<Driver> driversFound = new ArrayList<>();

        when(driverService.findByFirstName(anyString())).thenReturn(driversFound);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(DRIVER_URL_BASE + DriverConstant.DRIVER_ADVANCED_SEARCH)
                .accept(MediaType.APPLICATION_JSON)
                .param("firstname", "firstname")
                .param("null", "null")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Driver> drivers = JsonUtils.deserializeStringToList(contentAsString, Driver.class);

        Assertions.assertAll("Check condition", () -> Assertions.assertEquals(0, drivers.size()));

        verify(driverService, times(1)).findByFirstName(anyString());

    }

    @Test
    void should_delete_a_driver_by_id() throws Exception {
        Driver driver = Driver.builder()
                .id(1L)
                .build();

        when(driverService.deleteDriverById(anyLong())).thenReturn(driver);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(DRIVER_URL_BASE + DriverConstant.DELETE_DRIVER_BY_ID, "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Driver deletedDriver = JsonUtils.deserializeStringToObject(contentAsString, Driver.class);

        org.assertj.core.api.Assertions.assertThat(deletedDriver.getId()).isEqualTo(driver.getId());

        verify(driverService, times(1)).deleteDriverById(anyLong());

    }


    @Test
    void should_create_a_driver() throws Exception {
        Driver driver = Driver.builder()
                .id(1L)
                .build();

        when(driverService.createDriver(any(Driver.class))).thenReturn(driver);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(DRIVER_URL_BASE + DriverConstant.CREATE_DRIVER)
                .content(JsonUtils.serializeObjectToString(driver))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Driver createdDriver = JsonUtils.deserializeStringToObject(contentAsString, Driver.class);

        org.assertj.core.api.Assertions.assertThat(createdDriver.getId()).isEqualTo(driver.getId());

        verify(driverService, times(1)).createDriver(any(Driver.class));
    }

}