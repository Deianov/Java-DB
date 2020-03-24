package com.cardealer.repository;

import com.cardealer.model.entity.Part;
import com.cardealer.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Part findByNameAndPrice(String name, BigDecimal price);
}
