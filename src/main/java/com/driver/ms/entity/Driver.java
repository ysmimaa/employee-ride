package com.driver.ms.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.driver.ms.common.constant.DriverConstant.TABLE_NAME;

/**
 * Entity Driver
 */

@EqualsAndHashCode
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = TABLE_NAME)
public class Driver extends IdEntity {

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Embedded
    private Address address;

    @Column(name = "contractType")
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Column(name = "HIRED_DATE")
    private LocalDateTime hiredDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "journey_id")
    private Long journeyId;

    @Column(name = "company_id")
    private Long companyId;

    @Transient
    private Journey journey;

    @Transient
    private Company company;

    private boolean isValid;
}
