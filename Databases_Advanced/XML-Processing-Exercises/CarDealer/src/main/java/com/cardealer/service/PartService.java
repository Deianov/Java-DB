package com.cardealer.service;


import com.cardealer.model.dto.seed.PartSeedDto;
import com.cardealer.model.entity.Part;
import com.cardealer.model.entity.Supplier;

import java.util.List;
import java.util.Set;

public interface PartService {
    void seedParts(List<PartSeedDto> partSeedDtos);

    Set<Part> getRandomParts();
}
