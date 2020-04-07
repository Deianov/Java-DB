package softuni.workshop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.service.CompanyService;
import softuni.workshop.service.service.EmployeeService;
import softuni.workshop.service.service.ProjectService;


@Controller
@RequestMapping("/import/xml")
public class ImportController extends BaseController {

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ImportController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }


    @GetMapping("")
    public ModelAndView xmls(){

        boolean[] areImported = {
                companyService.areImported(),
                projectService.areImported(),
                employeeService.areImported()
        };

        ModelAndView view = new ModelAndView("xml/import-xml");
        view.addObject("areImported", areImported);
        return view;
    }

    @GetMapping("/companies")
    public ModelAndView companies(){
        ModelAndView view = new ModelAndView("xml/import-companies");
        view.addObject("companies", companyService.readCompaniesXmlFile());
        return view;
    }

    @PostMapping("/companies")
    public ModelAndView importCompanies(){
        companyService.importCompanies();
        return super.redirect("");
    }

    @GetMapping("/projects")
    public ModelAndView projects(){
        ModelAndView view = new ModelAndView("xml/import-projects");
        view.addObject("projects", projectService.readProjectsXmlFile());
        return view;
    }

    @PostMapping("/projects")
    public ModelAndView importProjects(){
        projectService.importProjects();
        return super.redirect("");
    }

    @GetMapping("/employees")
    public ModelAndView employees(){
        ModelAndView view = new ModelAndView("xml/import-employees");
        view.addObject("employees", employeeService.readEmployeesXmlFile());
        return view;
    }

    @PostMapping("/employees")
    public ModelAndView importEmployees(){
        employeeService.importEmployees();
        return super.redirect("");
    }
}
