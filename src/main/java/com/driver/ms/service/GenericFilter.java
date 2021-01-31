package com.driver.ms.service;

import com.driver.ms.entity.Driver;

public interface GenericFilter {
    boolean apply(Driver driver);
}
