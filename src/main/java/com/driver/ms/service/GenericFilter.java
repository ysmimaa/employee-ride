package com.driver.ms.service;

import com.driver.ms.entity.Driver;

@FunctionalInterface
public interface GenericFilter {
    boolean apply(Driver driver);
}
