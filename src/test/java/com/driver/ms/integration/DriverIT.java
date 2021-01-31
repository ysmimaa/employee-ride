package com.driver.ms.integration;

import com.driver.ms.common.constant.DriverConstant;
import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.common.utils.JsonUtils;
import com.driver.ms.entity.Driver;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@ActiveProfiles
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DriverIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_a_list_of_users() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(DriverConstant.BASE_URL.concat(DriverConstant.DRIVERS)))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        List<Driver> drivers = JsonUtils.deserializeStringToList(content, Driver.class);

        MatcherAssert.assertThat(drivers, IsCollectionWithSize.hasSize(6));
    }

    @Test
    void should_return_an_empty_list_of_users() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(DriverConstant.BASE_URL.concat(DriverConstant.DRIVER)))
                .andReturn();

        List<Driver> drivers = JsonUtils.deserializeStringToList(result.getResponse().getContentAsString(), Driver.class);

        MatcherAssert.assertThat(drivers, IsEmptyCollection.empty());
    }

    @Rollback
    @Test
    void should_create_a_new_driver() throws Exception {
        DriverDto driverToBeCreated = DriverDto.builder().firstName("integration-firstName").build();
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(DriverConstant.BASE_URL.concat(DriverConstant.DRIVER).concat(DriverConstant.CREATE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.serializeObjectToString(driverToBeCreated))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        String createdDriverInJsonFormat = result.getResponse().getContentAsString();

        DriverDto createdDriver = JsonUtils.deserializeStringToObject(createdDriverInJsonFormat, DriverDto.class);

        Assertions.assertThat(createdDriver).isEqualToComparingFieldByField(driverToBeCreated);

    }
}


