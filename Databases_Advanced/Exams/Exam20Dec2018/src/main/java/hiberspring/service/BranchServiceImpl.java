package hiberspring.service;

import hiberspring.constant.Constants;
import hiberspring.domain.dtos.BranchSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.JsonParser;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static hiberspring.constant.Constants.*;
import static hiberspring.constant.Constants.INCORRECT_DATA_MESSAGE;


@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final JsonParser jsonParser;
    private final TownService townService;

    @Autowired
    public BranchServiceImpl(BranchRepository repository, ModelMapper mapper, ValidationUtil validationUtil, JsonParser jsonParser, TownService townService) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.jsonParser = jsonParser;
        this.townService = townService;
    }

    @Override
    public Optional<Branch> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Boolean branchesAreImported() {
        return repository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return FileUtil.read(Constants.FILE_BRANCHES);
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        StringBuilder result = new StringBuilder();

        BranchSeedDto[] dtos = jsonParser
                .objectFromFile(FILE_BRANCHES, BranchSeedDto[].class);

        if(dtos == null || dtos.length == 0){
            return (NOT_FOUND);
        }

        for (BranchSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                if(getByName(dto.getName()).isEmpty()){

                    Branch branch = mapper.map(dto, Branch.class);
                    Town town = townService.getByName(dto.getTown()).orElse(null);

                    if(town != null){

                        branch.setTown(town);
                        repository.saveAndFlush(branch);

                        result.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                                "Branch",
                                branch.getName())
                        );

                    } else {
                        result.append(INCORRECT_DATA_MESSAGE);
                    }
                } else {
                    result.append(EXISTS);
                }
            }else {
                result.append(INCORRECT_DATA_MESSAGE);
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }
}
