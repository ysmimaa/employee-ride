package com.driver.ms.automation.driver;

import com.driver.ms.entity.Driver;
import com.driver.ms.service.DriverService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class FindDriverByFirstNameStepDef {

    private DriverService driverService;
    private List<Driver> listOfFoundDrivers;

    @Given("A valid param like {string}")
    public void aValidFirstName(String firstName) {
        listOfFoundDrivers = Arrays.asList(Driver.builder().id(1L).firstname(firstName).build());
    }

    @When("calling the findDriverByFirstName service")
    public void callingTheFindDriverByFirstNameService() {
        driverService = Mockito.mock(DriverService.class);
        Mockito.when(driverService.findByFirstName(ArgumentMatchers.anyString())).thenReturn(listOfFoundDrivers);
    }

    @Then("the driver with the param {string} should be returned")
    public void theDriverWithTheFirstNameShouldBeReturned(String firstName) {
        Assertions.assertEquals(firstName, listOfFoundDrivers.get(0).getFirstname());
    }

    @Then("the count should return {int}")
    public void theCountShouldReturn(int driverCount) {
        Assertions.assertEquals(1, driverCount);
    }
}
