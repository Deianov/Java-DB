package softuni.workshop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entity.Company;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByName(String name);
    Optional<Company> findByName(String name);
}