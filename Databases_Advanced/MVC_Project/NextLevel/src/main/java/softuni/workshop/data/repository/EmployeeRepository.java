package softuni.workshop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entity.Employee;
import softuni.workshop.service.model.EmployeeViewModel;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByFistNameAndLastNameAndAge(String firstName, String lastName, int age);
    Optional<Employee> findByFistNameAndLastNameAndAge(String firstName, String lastName, int age);

    @Query("select new softuni.workshop.service.model.EmployeeViewModel(e.fistName, e.lastName, e.age, e.project.name) from Employee e where e.age > ?1")
    Collection<EmployeeViewModel> findEmployeesByAgeGreaterThan(int age);
}
