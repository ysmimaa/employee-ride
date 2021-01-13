package com.driver.ms.integration;

import com.driver.ms.service.DriverService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DriverIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverService driverService;


    /*@Disabled("Disable until defining the list of users behavior")
    @Test
    void should_return_a_list_of_users() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(DriverConstant.BASE_URL.concat(DriverConstant.DRIVERS)))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        List<Driver> drivers = JsonUtils.deserializeStringToList(content, Driver.class);

        MatcherAssert.assertThat(drivers, IsCollectionWithSize.hasSize(6));
    }*/
}
