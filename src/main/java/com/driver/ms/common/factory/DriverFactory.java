package com.driver.ms.common.factory;

import com.driver.ms.common.dto.DriverDto;
import com.driver.ms.entity.Driver;

public interface DriverFactory {
    Driver convertDriverDtoToDriverEntity(DriverDto driverDto);

    DriverDto convertDriverEntityToDriverDto(Driver driverEntity);
}
