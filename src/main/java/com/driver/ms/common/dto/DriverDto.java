package com.driver.ms.common.dto;

import com.driver.ms.entity.Address;
import com.driver.ms.entity.ContractType;
import com.driver.ms.entity.IdEntityNonePersisted;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@SuperBuilder
public class DriverDto extends IdEntityNonePersisted {
    private String firstName;
    private String lastName;
    private Address address;
    private ContractType contractType;
    private LocalDateTime hiredDate;
}
