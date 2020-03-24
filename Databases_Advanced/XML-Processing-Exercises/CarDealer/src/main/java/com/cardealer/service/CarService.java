package com.cardealer.service;

import com.cardealer.model.dto.query2.CarViewRootDto;
import com.cardealer.model.dto.query4.CarAndPartsViewRootDto;
import com.cardealer.model.dto.query6.SalesViewRootDto;
import com.cardealer.model.dto.seed.CarSeedDto;
import com.cardealer.model.entity.Car;

import java.util.List;

public interface CarService {
    void seedCars(List<CarSeedDto> carSeedDtos);
    Car getRandomCar();

    CarViewRootDto query2CarsFromMakeToyota();
    CarAndPartsViewRootDto query4CarsWithTheirListOfParts();
}
