package softuni.workshop.service.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.constant.Constants;
import softuni.workshop.data.dto.ProjectSeedDto;
import softuni.workshop.data.dto.ProjectSeedRootDto;
import softuni.workshop.data.entity.Company;
import softuni.workshop.data.entity.Project;
import softuni.workshop.data.repository.ProjectRepository;
import softuni.workshop.service.model.ProjectViewModel;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidationUtil;
import softuni.workshop.util.XmlParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CompanyService companyService;
    private final Gson gson;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, CompanyService companyService, Gson gson) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.companyService = companyService;
        this.gson = gson;
    }

    @Override
    public Optional<Project> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void importProjects(){
        ProjectSeedRootDto rootDto = xmlParser
                .unmarshalFromFile(Constants.FILE_PATH_PROJECTS, ProjectSeedRootDto.class);

        if(rootDto == null){
            return;
        }

        Collection<ProjectSeedDto> dtos = rootDto.getProjects();

        if(dtos == null || dtos.size() == 0){
            return;
        }

        for (ProjectSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                if(!repository.existsByName(dto.getName())) {

                    Company company = companyService
                            .getByName(dto.getCompany().getName()).orElse(null);

                    if (company != null){

                        Project project = mapper.map(dto, Project.class);
                        project.setCompany(company);
                        repository.saveAndFlush(project);
                    }
                }
            }
        }
    }

    @Override
    public boolean areImported() {
       return repository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() {
      return FileUtil.read(Constants.FILE_PATH_PROJECTS);
    }

    @Override
    public String exportFinishedProjects(){
        return
        repository.findProjectsByFinishedIsTrue()
                .stream()
                .map(project -> mapper.map(project, ProjectViewModel.class).toString())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public Collection<ProjectViewModel> findAll() {
        return
        repository.findAll()
                .stream()
                .map(project -> mapper.map(project, ProjectViewModel.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String exportProjectsToJson() {
        return gson.toJson(this.findAll());
    }
}
