package softuni.workshop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entity.Project;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByName(String name);
    Optional<Project> findByName(String name);
    Collection<Project> findProjectsByFinishedIsTrue();
}
