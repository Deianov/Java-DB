package softuni.workshop.service.service;


import softuni.workshop.data.entity.Company;
import softuni.workshop.service.model.CompanyViewModel;

import java.util.Collection;
import java.util.Optional;

public interface CompanyService {
    Optional<Company> getByName(String name);

    void importCompanies();

    boolean areImported();

    String readCompaniesXmlFile();

    Collection<CompanyViewModel> findAll();

    String exportAllToJson();
}
