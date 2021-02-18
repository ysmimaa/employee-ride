package com.driver.ms.service.impl;

import com.driver.ms.common.constant.DriverConstant;
import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.common.factory.DriverFactory;
import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.driver.ms.exception.BadParamException;
import com.driver.ms.exception.DriverNotFoundException;
import com.driver.ms.repository.DriverRepository;
import com.driver.ms.service.DriverService;
import com.driver.ms.service.GenericFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private DriverFactory driverFactory;
    private GenericFilter driverGenericFilter;

    public DriverServiceImpl(final DriverRepository driverRepository,
                             final DriverFactory driverFactory,
                             @Qualifier("filterDriverByFirstName") GenericFilter filterDriverByFirstName) {
        this.driverRepository = driverRepository;
        this.driverFactory = driverFactory;
        this.driverGenericFilter = filterDriverByFirstName;
    }

    @Override
    public List<Driver> getListOfDriver() {
        log.info("Fetch the list of the driver");
        return driverRepository.findAll();
    }

    @Override
    public DriverDto createDriver(DriverDto driverDto) {
        log.info("Create a new driver");
        if (driverDto == null) {
            throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
        }
        log.info("Drive has been created");
        Driver savedDriver = driverRepository.save(driverFactory.convertDriverDtoToDriverEntity(driverDto));
        return driverFactory.convertDriverEntityToDriverDto(savedDriver);

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
    public DriverDto updateDriver(DriverDto driverDto) throws JsonProcessingException {
        if (driverDto == null) {
            throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
        }
        Long driverId = driverDto.getId();
        return Optional.of(findDriverById(driverId))
                .map(driverToUpdate -> {
                    driverToUpdate.setFirstName(driverDto.getFirstName());
                    driverToUpdate.setLastName(driverDto.getLastName());

                    try {
                        log.info("Updating driverDto with id {} ", new ObjectMapper().writeValueAsString(driverDto.getId()));
                    } catch (JsonProcessingException exception) {
                        log.error(exception.getMessage());
                    }
                    return driverFactory.convertDriverEntityToDriverDto(
                            driverRepository.save(driverFactory.convertDriverDtoToDriverEntity(driverToUpdate))
                    );
                }).orElseThrow();
    }

    @Override
    public DriverDto findDriverById(Long id) throws JsonProcessingException {
        if (id == null) {
            throw new BadParamException(DriverConstant.PLEASE_PROVIDE_A_VALID_DRIVER);
        }
        log.info("Find driver by id : {} ", new ObjectMapper().writeValueAsString(id));
        return driverRepository.findById(id)
                .map(driver -> driverFactory.convertDriverEntityToDriverDto(driver))
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
    public DriverDto deleteDriverById(Long id) throws JsonProcessingException {
        if (id == null) {
            throw new BadParamException("Please provide a valid driver id");
        }
        log.info("Deleting the driver by id {} ", id);
        return Optional.of(findDriverById(id))
                .map(driver -> {
                    log.info("The driver with the id {} has been deleted", id);
                    driverRepository.delete(driverFactory.convertDriverDtoToDriverEntity(driver));
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

    @Override
    public List<Driver> filterDriversWithTheSameFirstName() {
        return driverRepository.findAll().stream()
                .filter(driverGenericFilter::apply)
                .collect(Collectors.toList());
    }
}
