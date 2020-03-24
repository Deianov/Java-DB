package com.cardealer.service;

import com.cardealer.model.dto.query3.SupplierAndPartsCountRootViewDto;
import com.cardealer.model.dto.query3.SupplierAndPartsCountViewDto;
import com.cardealer.model.dto.seed.SupplierSeedDto;
import com.cardealer.model.entity.Supplier;
import com.cardealer.repository.SupplierRepository;
import com.cardealer.util.RandomUtil;
import com.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final RandomUtil randomUtil;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationUtil validationUtil, RandomUtil randomUtil) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.randomUtil = randomUtil;
    }

    @Override
    public boolean isEmpty() {
        return this.supplierRepository.count() == 0;
    }

    @Override
    public void seedSuppliers(List<SupplierSeedDto> supplierSeedDtos) {
        supplierSeedDtos
                .forEach(supplierSeedDto -> {
                    if(this.validationUtil.isValid(supplierSeedDto)){
                        if(this.supplierRepository
                                .findByName(supplierSeedDto.getName()) == null){

                            Supplier supplier = this.modelMapper
                                    .map(supplierSeedDto, Supplier.class);

                            this.supplierRepository.saveAndFlush(supplier);

                        }else {
                            System.out.println("Already in DB");
                        }
                    }else {
                        this.validationUtil.printViolations(supplierSeedDto);
                    }
                });
    }

    @Override
    public Supplier getRandomSupplier() {
        long randomId = this.randomUtil.randomId(this.supplierRepository.count());
        return this.supplierRepository.getOne(randomId);
    }

    @Override
    public SupplierAndPartsCountRootViewDto query3LocalSuppliers() {

        List<SupplierAndPartsCountViewDto> dtos =
                supplierRepository.findAllByImporterIsFalse()
                .stream()
                .map(supplier -> {
                    SupplierAndPartsCountViewDto dto = modelMapper
                            .map(supplier, SupplierAndPartsCountViewDto.class);
                    dto.setPartsCount(supplier.getParts().size());
                    return dto;
                })
                .collect(Collectors.toList());

        return new SupplierAndPartsCountRootViewDto(dtos);
    }
}
