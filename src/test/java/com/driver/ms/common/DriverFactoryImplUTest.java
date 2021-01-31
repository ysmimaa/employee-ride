package com.driver.ms.common;

import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.common.factory.DriverFactoryImpl;
import com.driver.ms.entity.Driver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("*** Driver factory ***")
class DriverFactoryImplUTest {

    private DriverFactoryImpl driverFactory;

    @BeforeEach
    void init() {
        driverFactory = new DriverFactoryImpl();
    }

    @DisplayName("Should convert driver dto to driver entity")
    @Test
    void should_return_driver_entity_when_passing_driver_dto() {
        DriverDto driverDtoTobeConverted = DriverDto.builder()
                .firstName("firstname")
                .lastName("lastname")
                .build();
        Driver convertedDriverEntity = driverFactory.convertDriverDtoToDriverEntity(driverDtoTobeConverted);
        Assertions.assertThat(convertedDriverEntity.getLastname()).isEqualTo(driverDtoTobeConverted.getLastName());
    }

    @DisplayName("Should convert driver entity to driver dto")
    @Test
    void should_return_driver_dto_when_passing_driver_entity() {
        Driver driverEntityTobeConverted = Driver.builder()
                .firstname("firstname")
                .lastname("lastname")
                .build();
        DriverDto convertedDriverDto = driverFactory.convertDriverEntityToDriverDto(driverEntityTobeConverted);
        Assertions.assertThat(convertedDriverDto.getLastName()).isEqualTo(driverEntityTobeConverted.getLastname());
    }
}