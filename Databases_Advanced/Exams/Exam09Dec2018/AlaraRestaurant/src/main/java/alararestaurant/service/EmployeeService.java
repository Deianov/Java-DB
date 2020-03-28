package alararestaurant.service;

import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Boolean employeesAreImported();

    String readEmployeesJsonFile();

    String importEmployees(String employees);

    Optional<Employee> getByName(String name);

    List<Employee> getEmployeesByPositionOrderByNameAsc(Position position);
}
