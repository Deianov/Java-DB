package alararestaurant.repository;

import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByName(String name);

    List<Employee> findEmployeesByPositionOrderByNameAscOrdersIdAsc(Position position);
}
