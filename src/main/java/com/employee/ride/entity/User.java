package com.employee.ride.entity;

import com.employee.ride.common.constant.UserConstant;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity User
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = UserConstant.TABLE_NAME)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Column
    private String username;

    @Column
    private String password;
}
