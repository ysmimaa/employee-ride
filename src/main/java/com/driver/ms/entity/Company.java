package com.driver.ms.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.driver.ms.common.constant.CompanyConstant.TABLE_NAME;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

/**
 * Company entity
 */

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = TABLE_NAME)
public class Company extends IdEntity {

    @Column
    private String name;

    @Column
    private String activity;

    @Embedded
    private Address address;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = ALL)
    private List<Driver> drivers = new ArrayList<>();

    @OneToMany(cascade = ALL)
    @JoinTable(name = "CONTRACT", joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "factory_id"))
    private List<Factories> factories = new ArrayList<>();
}
