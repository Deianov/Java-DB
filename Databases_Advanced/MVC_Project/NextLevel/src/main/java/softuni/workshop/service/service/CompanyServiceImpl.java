package softuni.workshop.service.service;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.constant.Constants;
import softuni.workshop.data.dto.CompanySeedDto;
import softuni.workshop.data.dto.CompanySeedRootDto;
import softuni.workshop.data.entity.Company;
import softuni.workshop.data.repository.CompanyRepository;
import softuni.workshop.service.model.CompanyViewModel;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidationUtil;
import softuni.workshop.util.XmlParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final Gson gson;

    @Autowired
    public CompanyServiceImpl(CompanyRepository repository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, Gson gson) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.gson = gson;
    }

    @Override
    public Optional<Company> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void importCompanies() {

        CompanySeedRootDto rootDto = xmlParser
                .unmarshalFromFile(Constants.FILE_PATH_COMPANIES, CompanySeedRootDto.class);

        if (rootDto == null) {
            return;
        }

        Collection<CompanySeedDto> dtos = rootDto.getCompanies();

        if (dtos == null || dtos.size() == 0) {
            return;
        }

        for (CompanySeedDto dto : dtos) {

            if (validationUtil.isValid(dto)) {

                if (!repository.existsByName(dto.getName())) {

                    Company company = mapper.map(dto, Company.class);
                    repository.saveAndFlush(company);
                }
            }
        }
    }

    @Override
    public boolean areImported() {
        return repository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() {
        return FileUtil.read(Constants.FILE_PATH_COMPANIES);
    }

    @Override
    public Collection<CompanyViewModel> findAll() {
        return
        repository.findAll()
                .stream()
                .map(company -> mapper.map(company, CompanyViewModel.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String exportAllToJson() {
        return gson.toJson(this.findAll());
    }
}
