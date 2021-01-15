package com.driver.ms.common.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Class that centralize the user constants
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverConstant {

    public static final String BASE_URL = "/api/";

    public static final String TABLE_NAME = "driver";

    public static final String DRIVERS = "drivers";

    public static final String DELETE_DRIVER_BY_ID = "driver/{id}";

    public static final String FIND_DRIVER_BY_ID = "driver/findById/{id}";

    public static final String DRIVER_ADVANCED_SEARCH = "advanceFilter";

    public static final String CREATE = "create";

    public static final String UPDATE_DRIVER = "update";

    public static final String BASIC_AUTH = "basic-auth";

    public static final String USER = "user";

    public static final String ID = "id";

    public static final String PLEASE_PROVIDE_A_VALID_DRIVER = "Please provide a valid driver";
    public static final String DRIVER = "driver/";
}
