package com.driver.ms.service.impl;

import com.driver.ms.common.constant.DriverConstant;
import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.driver.ms.exception.BadParamException;
import com.driver.ms.exception.DriverNotFoundException;
import com.driver.ms.repository.DriverRepository;
import com.driver.ms.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;

    @Autowired
    public DriverServiceImpl(final DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public List<Driver> getListOfDriver() {
        log.info("Fetch the list of the driver");
        return driverRepository.findAll();
    }

    @Override
    public Driver createDriver(Driver driver) {
        log.info("Create a new driver");
        if (driver == null) {
            throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
        }
        log.debug("Drive has been created");
        return driverRepository.save(driver);

    }

    @Override
    public List<Driver> findByFirstName(String name) {
        if (name == null) {
            throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
        }
        log.info("Fetching the list of driver based on the firstName criteria");
        return driverRepository.findByFirstname(name);
    }

    @Override
    public Driver updateDriver(Driver driver) {
        if (driver == null) {
            throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
        }
        Long driverId = driver.getId();
        return Optional.of(findDriverById(driverId))
                .map(driverToUpdate -> {
                    driverToUpdate.setFirstname(driver.getFirstname());
                    driverToUpdate.setLastname(driver.getLastname());
                    driverToUpdate.setJourney(driver.getJourney());
                    driverToUpdate.setFirstname(driver.getFirstname());

                    log.info("Updating driver with id {} : ", driver.getId());
                    return driverRepository.save(driverToUpdate);
                }).orElseThrow();
    }

    @Override
    public Driver findDriverById(Long id) {
        if (id == null) {
            throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
        }
        log.info("Find driver by id : {} ", id);
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver with id " + id + " not found"));
    }

    @Override
    public Driver findByAddressPhone(String phone) {
        if (phone.isEmpty() || phone.isBlank()) {
            log.error("The argument {} is not valid", phone);
            throw new BadParamException("Please provide a valid phone number");
        }
        log.info("Find driver by phone {} : ", phone);
        return driverRepository.findByAddressPhone(phone)
                .orElseThrow(() -> new DriverNotFoundException("Driver with id " + phone + " not found"));
    }

    @Override
    public Map<Journey, List<Driver>> getGroupedDriversByJourney() {
        return driverRepository.findAll().stream()
                .collect(Collectors.groupingBy(Driver::getJourney));
    }

    @Override
    public Driver deleteDriverById(Long id) {
        if (id == null) {
            throw new BadParamException("Please provide a valid driver id");
        }
        log.info("Deleting the driver by id {} ", id);
        return Optional.of(findDriverById(id))
                .map(driver -> {
                    log.info("The driver with the id {} has been deleted", id);
                    driverRepository.delete(driver);
                    return driver;
                }).orElse(null);
    }


    @Override
    public String getJourneyMetrics(Driver driver) {
        return null;
    }

    @Override
    public Driver findDriverByLastName(String lastName) {
        if (StringUtils.isEmpty(lastName)) {
            throw new BadParamException("Please provide a valid lastName");
        }
        log.info("Finding the driver {} ", lastName);
        return driverRepository.findByLastname(lastName)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }
}
