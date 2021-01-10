package com.driver.ms.service.impl;

import com.driver.ms.common.constant.DriverConstant;
import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.driver.ms.exception.BadParamException;
import com.driver.ms.exception.DriverNotFoundException;
import com.driver.ms.exception.ExistingDriverException;
import com.driver.ms.repository.DriverRepository;
import com.driver.ms.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
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
        if (driver != null) {
            Long driverId = driver.getId();
            Driver foundDriverById = findDriverById(driverId);
            if (driverId != null && foundDriverById != null
                    && foundDriverById.getId().compareTo(driverId) == 0) {
                log.error("Driver {} already exist", driver);
                throw new ExistingDriverException("Driver already exist");
            }
            log.debug("Drive has been created");
            return driverRepository.save(driver);
        }
        throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
    }

    @Override
    public List<Driver> findByFirstName(String name) {
        if (name == null) {
            throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
        }
        log.info("Fetching the list of driver based on the firstName criteria");
        return driverRepository.findByFirstName(name);
    }

    @Override
    public Driver updateDriver(Driver driver) {
        if (driver != null) {
            Long driverId = driver.getId();
            if (driverId != null) {
                log.debug("find the driver by id : {} ", driverId);
                Driver driverById = findDriverById(driverId);
                if (driverById != null) {
                    log.debug("The driver has been saved");
                    return driverRepository.save(driver);
                }
            }
        }
        throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
    }

    @Override
    public Driver findDriverById(Long id) {
        if (id != null) {
            log.debug("Find driver by id : {} ", id);
            return driverRepository.findById(id)
                    .orElseThrow(() -> new DriverNotFoundException("Driver with id " + id + " not found"));
        }
        throw new BadParamException("Please provide a valid driver id");
    }

    @Override
    public Driver findByAddressPhone(String phone) {
        if (phone.isEmpty() || phone.isBlank()) {
            log.error("The argument {} is not valid", phone);
            throw new BadParamException("Please provide a valid phone number");
        }
        log.debug("Find driver by phone {} : ", phone);
        return driverRepository.findByAddressPhone(phone)
                .orElseThrow(() -> new DriverNotFoundException("Driver with id " + phone + " not found"));
    }

    @Override
    public Map<Journey, List<Driver>> getGroupedDriversByJourney() {
        return driverRepository.findAll().stream().collect(Collectors.groupingBy(Driver::getJourney));
    }

    @Override
    public Driver deleteDriverById(Long id) {
        log.info("Deleting the driver by id {} ", id);
        if (id != null) {
            Driver driverById = findDriverById(id);
            if (driverById != null) {
                log.info("Deleting the driver by id {} ", id);
                driverRepository.delete(driverById);
                return driverById;
            }
        }
        throw new BadParamException("Please provide a valid driver id");
    }

    @Override
    public String getJourneyMetrics(Driver driver) {
        return null;
    }

    @Override
    public Driver findDriverByLastName(String lastName) {
        if (!StringUtils.isEmpty(lastName)) {
            return driverRepository.findByLastName(lastName)
                    .orElseThrow(() -> new RuntimeException("Driver not found"));
        }
        throw new BadParamException("Please provide a valid lastName");
    }
}
