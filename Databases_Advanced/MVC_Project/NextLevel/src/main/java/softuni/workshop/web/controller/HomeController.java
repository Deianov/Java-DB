package softuni.workshop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.service.CompanyService;
import softuni.workshop.service.service.EmployeeService;
import softuni.workshop.service.service.ProjectService;

@Controller
public class HomeController extends BaseController {

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public HomeController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }


    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/home")
    public ModelAndView home() {

        boolean areImported =
                companyService.areImported() &&
                projectService.areImported() &&
                employeeService.areImported();

        ModelAndView modelAndView  = new ModelAndView("home");
        modelAndView.addObject("areImported", areImported);

        return modelAndView;
    }
}
