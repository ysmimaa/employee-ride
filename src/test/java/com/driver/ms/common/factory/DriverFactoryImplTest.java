package com.driver.ms.common.factory;

import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.entity.Driver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("*** Driver factory ***")
class DriverFactoryImplTest {

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
}