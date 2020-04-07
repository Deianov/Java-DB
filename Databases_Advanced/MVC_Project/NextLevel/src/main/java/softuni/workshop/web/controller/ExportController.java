package softuni.workshop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.service.CompanyService;
import softuni.workshop.service.service.EmployeeService;
import softuni.workshop.service.service.ProjectService;

@Controller
public class ExportController extends BaseController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final CompanyService companyService;

    @Autowired
    public ExportController(EmployeeService employeeService, ProjectService projectService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.companyService = companyService;
    }

    @GetMapping("/export/project-if-finished")
    public ModelAndView exportFinishedProjects(){
        ModelAndView view = new ModelAndView("export/export-project-if-finished");
        view.addObject("projectsIfFinished", projectService.exportFinishedProjects());
        return view;
    }

    @GetMapping("/export/employees-above")
    public ModelAndView exportEmployeesAbove(){
        ModelAndView view = new ModelAndView("export/export-employees-with-age");
        view.addObject("employeesAbove", employeeService.exportEmployeesWithAgeAbove());
        return view;
    }

    @GetMapping("/export/employees-to-json")
    public ModelAndView exportEmployeesToJson(){
        ModelAndView view = new ModelAndView("export/export-employees-to-json");
        view.addObject("employeesJson", employeeService.exportEmployeesToJson());
        return view;
    }

    @GetMapping("/export/companies-to-json")
    public ModelAndView exportCompaniesToJson(){
        ModelAndView view = new ModelAndView("export/export-companies-to-json");
        view.addObject("companiesJson", companyService.exportAllToJson());
        return view;
    }
}
