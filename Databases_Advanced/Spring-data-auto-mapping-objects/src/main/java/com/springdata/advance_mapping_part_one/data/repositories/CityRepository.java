package com.springdata.advance_mapping_part_one.data.repositories;

import com.springdata.advance_mapping_part_one.data.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findByName(String name);
}
