package com.driver.ms.entity;


import com.driver.ms.common.constant.JourneyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Journey entity
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Journey extends IdEntityNonePersisted {
    private Address address;
    private Integer nbrOfPlaces;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Company company;
    private Driver driver;
    private JourneyStatus journeyStatus;
}
