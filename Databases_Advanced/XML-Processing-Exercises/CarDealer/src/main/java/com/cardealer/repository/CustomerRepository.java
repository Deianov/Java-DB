package com.cardealer.repository;

import com.cardealer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByNameAndBirthDate(String name, LocalDateTime birthDate);

    @Query("SELECT c FROM Customer AS c " +
            "ORDER BY c.birthDate, c.youngDriver")
    List<Customer> findByAllByBirthDateAndIsYoungDriver();

    @Query("select c from Customer c join c.sales s "+
            "where size(c.sales) > 0 "+
            "group by c.id"
    )
    List<Customer> findCustomersWithSales();
}
