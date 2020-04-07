package softuni.workshop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.workshop.service.model.CompanyViewModel;
import softuni.workshop.service.model.EmployeeViewModel;
import softuni.workshop.service.model.ProjectViewModel;
import softuni.workshop.service.service.CompanyService;
import softuni.workshop.service.service.EmployeeService;
import softuni.workshop.service.service.ProjectService;

import java.util.Collection;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final ProjectService projectService;

    @Autowired
    public RestController(EmployeeService employeeService, CompanyService companyService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.projectService = projectService;
    }

    @GetMapping("/api/json/employees")
    public Collection<EmployeeViewModel> employees(){
       return employeeService.findAll();
    }

    @GetMapping("/api/json/companies")
    public Collection<CompanyViewModel> companies(){
        return companyService.findAll();
    }

    @GetMapping("/api/json/projects")
    public Collection<ProjectViewModel> projects(){
        return projectService.findAll();
    }
}
