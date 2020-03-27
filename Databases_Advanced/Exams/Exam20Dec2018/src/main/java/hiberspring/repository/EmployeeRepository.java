package hiberspring.repository;

import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsEmployeeByCard(EmployeeCard card);

//    @Query(value =
//            "select * from employees e " +
//            "right join products p on e.branch_id = p.branch_id " +
//            "group by e.id " +
//            "having e.id is not null " +
//            "order by e.first_name, e.last_name, char_length(e.position) desc",
//            nativeQuery = true
//    )
    @Query("select e from Employee e where e.branch.products.size > 0 "+
            "order by concat(e.firstName, ' ', e.lastName), length(e.position) desc"

    )
    List<Employee> getAllSortedByFullNameAndPositionLength();
}
