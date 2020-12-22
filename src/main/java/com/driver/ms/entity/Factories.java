package com.driver.ms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "FACTORY")
public class Factories extends IdEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    private Address address;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

}
