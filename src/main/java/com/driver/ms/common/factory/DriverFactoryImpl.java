package com.driver.ms.common.factory;

import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.entity.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverFactoryImpl implements DriverFactory {

    public Driver convertDriverDtoToDriverEntity(DriverDto driverDto) {
        return Driver.builder()
                .firstname(driverDto.getFirstName())
                .lastname(driverDto.getLastName())
                .build();
    }

    @Override
    public DriverDto convertDriverEntityToDriverDto(Driver driverEntity) {
        return DriverDto.builder()
                .firstName(driverEntity.getFirstname())
                .lastName(driverEntity.getLastname())
                .build();
    }
}
