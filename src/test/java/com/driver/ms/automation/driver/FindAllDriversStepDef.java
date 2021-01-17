package com.driver.ms.automation.driver;

import com.driver.ms.entity.Driver;
import com.driver.ms.service.DriverService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllDriversStepDef {

    private DriverService driverService;
    private List<Driver> driverList;

    @Given("Service that return all drivers")
    public void serviceThatReturnAllDrivers() {
        driverService = Mockito.mock(DriverService.class);
    }

    @When("Calling the findAllDrivers service")
    public void callingTheFindAllDriversService() {
        driverList = Arrays.asList(Driver.builder().id(1L).build(), Driver.builder().id(2L).build());
        Mockito.when(driverService.getListOfDriver()).thenReturn(driverList);
    }

    @Then("All drivers should be displayed")
    public void allDriversShouldBeDisplayed() {
        Assertions.assertEquals(2, driverList.size(), "List of drivers must contain 2 elements");
    }
}
