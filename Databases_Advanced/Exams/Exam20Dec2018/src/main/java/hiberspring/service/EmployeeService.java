package hiberspring.service;

import hiberspring.domain.entities.Employee;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface EmployeeService {
   Optional<Employee> getByNames(String firstName, String lastName);

   Boolean employeesAreImported();

   String readEmployeesXmlFile() throws IOException;

   String importEmployees() throws JAXBException, FileNotFoundException;

   String exportProductiveEmployees();
}
