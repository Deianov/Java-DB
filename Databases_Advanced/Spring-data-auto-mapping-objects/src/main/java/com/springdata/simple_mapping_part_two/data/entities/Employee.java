package com.springdata.simple_mapping_part_two.data.entities;


import com.springdata.simple_mapping_part_two.data.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}

