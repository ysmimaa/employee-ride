package com.driver.ms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "key_generator")
    @TableGenerator(name = "key_generator", pkColumnName = "pk_name", pkColumnValue = "pk_value",
            table = "USER_KEY")
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Transient
    private String validatePassword;

    @Column(name = "EMAIL")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
