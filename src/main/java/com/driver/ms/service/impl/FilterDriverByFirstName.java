package com.driver.ms.service.impl;

import com.driver.ms.entity.Driver;
import com.driver.ms.service.GenericFilter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Component
public class FilterDriverByFirstName implements GenericFilter {

    private String firstName;

    @Override
    public boolean apply(Driver driver) {
        return driver.getFirstname().equals(firstName);
    }
}
