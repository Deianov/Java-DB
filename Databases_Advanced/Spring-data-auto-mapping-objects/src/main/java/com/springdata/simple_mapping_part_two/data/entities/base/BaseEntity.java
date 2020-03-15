package com.springdata.simple_mapping_part_two.data.entities.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public BaseEntity(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
