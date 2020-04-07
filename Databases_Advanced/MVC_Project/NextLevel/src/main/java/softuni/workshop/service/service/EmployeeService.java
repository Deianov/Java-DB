package softuni.workshop.service.service;

import softuni.workshop.service.model.EmployeeViewModel;

import java.util.Collection;

public interface EmployeeService {

    void importEmployees();

    boolean areImported();

    String readEmployeesXmlFile();

    String exportEmployeesWithAgeAbove();

    String exportEmployeesToJson();

    Collection<EmployeeViewModel> findAll();
}
