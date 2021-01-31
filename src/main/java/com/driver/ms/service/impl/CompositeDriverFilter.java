package com.driver.ms.service.impl;

import com.driver.ms.entity.Driver;
import com.driver.ms.service.GenericFilter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeDriverFilter implements GenericFilter {

    private final List<GenericFilter> genericFilters;

    public CompositeDriverFilter(List<GenericFilter> genericFilters) {
        this.genericFilters = genericFilters;
    }

    @Override
    public boolean apply(Driver driver) {
        return genericFilters.stream()
                .map(filter -> filter.apply(driver))
                .reduce(true, (a, b) -> a && b);
    }

    void addFilter(GenericFilter genericFilter) {
        genericFilters.add(genericFilter);
    }
}
