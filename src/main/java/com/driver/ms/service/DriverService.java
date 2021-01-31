package com.driver.ms.service;

import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface DriverService {

    /**
     * Method that return the list of driver
     *
     * @return a list of driver
     */
    List<Driver> getListOfDriver();

    /**
     * Method for creating a driver
     *
     * @param driverDto
     * @return the driver created
     */
    DriverDto createDriver(DriverDto driverDto);

    /**
     * Method that return a list of found drivers base on the criteria
     *
     * @return list of dirvers found
     */
    List<Driver> findByFirstName(String name);

    /**
     * Method that update an existing driver
     *
     * @param driverDto given driver
     * @return the driver that has been updated
     */
    Driver updateDriver(DriverDto driverDto) throws JsonProcessingException;

    /**
     * Method that return a driver by his id
     *
     * @param id given id
     * @return the driver if exist
     */
    Driver findDriverById(Long id) throws JsonProcessingException;

    /**
     * Method that return a driver by his phone
     *
     * @param phone
     * @return a matching driver
     */
    Driver findByAddressPhone(String phone);

    /**
     * Method that return a grouped driver's list by journey
     *
     * @return the list of drivers grouped by journey
     */
    Map<Journey, List<Driver>> getGroupedDriversByJourney();

    /**
     * Method for deleting the driver by his id
     *
     * @param id driver id
     * @return deleted id
     */
    Driver deleteDriverById(Long id) throws JsonProcessingException;

    /**
     * Method that return the driver's metrics
     *
     * @return driver's metrics
     */
    String getJourneyMetrics(Driver driver);

    /**
     * Method that return a driver by his lastName
     *
     * @param lastName
     * @return driver by his lastName
     */
    Driver findDriverByLastName(String lastName);

    /**
     * Method that return the list of drivers who have the same firstName
     *
     * @return list of drivers
     */
    List<Driver> filterDriversWithTheSameFirstName();
}
