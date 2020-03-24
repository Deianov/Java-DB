package com.cardealer.service;

import com.cardealer.model.dto.seed.PartSeedDto;
import com.cardealer.model.entity.Part;
import com.cardealer.model.entity.Supplier;
import com.cardealer.repository.PartRepository;
import com.cardealer.util.RandomUtil;
import com.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SupplierService supplierService;
    private final RandomUtil randomUtil;

    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, SupplierService supplierService, RandomUtil randomUtil) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierService = supplierService;
        this.randomUtil = randomUtil;
    }

    @Override
    public void seedParts(List<PartSeedDto> partSeedDtos) {
        partSeedDtos
                .forEach(partSeedDto -> {
                    if (this.validationUtil.isValid(partSeedDto)) {
                        if (this.partRepository
                                .findByNameAndPrice(partSeedDto.getName(), partSeedDto.getPrice()) == null) {

                            Part part = this.modelMapper.map(partSeedDto, Part.class);
                            part.setSupplier(this.supplierService.getRandomSupplier());

                            this.partRepository.saveAndFlush(part);

                        }else {
                            System.out.println("Already in DB");
                        }
                    } else {
                        this.validationUtil.printViolations(partSeedDto);
                    }
                });
    }

    @Override
    public Set<Part> getRandomParts() {
        Set<Part> resultSet = new HashSet<>();
        int randomCounter = this.randomUtil.randomIndex(10) + 10;

        for (int i = 0; i < randomCounter; i++) {
            long randomId = this.randomUtil.randomId(this.partRepository.count());
            resultSet.add(this.partRepository.getOne(randomId));
        }
        return resultSet;
    }
}
