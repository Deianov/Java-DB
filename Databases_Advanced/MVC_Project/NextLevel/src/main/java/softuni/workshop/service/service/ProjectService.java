package softuni.workshop.service.service;

import softuni.workshop.data.entity.Project;
import softuni.workshop.service.model.ProjectViewModel;

import java.util.Collection;
import java.util.Optional;

public interface ProjectService {
    Optional<Project> getByName(String name);

    void importProjects();

    boolean areImported();

    String readProjectsXmlFile();

    String exportFinishedProjects();

    Collection<ProjectViewModel> findAll();

    String exportProjectsToJson();
}
