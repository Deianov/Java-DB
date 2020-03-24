package com.cardealer.service;

import com.cardealer.model.dto.query3.SupplierAndPartsCountRootViewDto;
import com.cardealer.model.dto.seed.SupplierSeedDto;
import com.cardealer.model.entity.Supplier;

import java.util.List;

public interface SupplierService {
    boolean isEmpty();
    void seedSuppliers(List<SupplierSeedDto> supplierSeedDtos);

    Supplier getRandomSupplier();
    SupplierAndPartsCountRootViewDto query3LocalSuppliers();
}
