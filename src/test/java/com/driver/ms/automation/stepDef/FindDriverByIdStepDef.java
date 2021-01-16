package com.driver.ms.automation.stepDef;

import com.driver.ms.entity.Driver;
import com.driver.ms.service.DriverService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.util.List;

public class FindDriverByIdStepDef {

    private DriverService driverService;
    List<Driver> driverServiceByFirstName;

    @When("I provide a valid firstName")
    public void iProvideAValidFirstName() {
        //driverServiceByFirstName = driverService.findByFirstName("youssef");
    }

    @Then("the driver with the firstName should be returned")
    public void theDriverWithTheFirstNameShouldBeReturned() {
        //Assertions.assertThat(driverServiceByFirstName).contains();
    }
}
