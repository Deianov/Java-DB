package com.springdata.simple_mapping_part_two.data.repositories;

import com.springdata.simple_mapping_part_two.data.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findByName(String name);
}
