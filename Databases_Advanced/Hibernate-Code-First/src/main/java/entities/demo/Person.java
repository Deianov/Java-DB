package entities.demo;

import entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Person() {}
}
