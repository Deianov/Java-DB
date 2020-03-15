package com.springdata.advance_mapping_part_one.data.entities;

import com.springdata.advance_mapping_part_one.data.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class City extends BaseEntity {

    @Basic
    private String name;
}
