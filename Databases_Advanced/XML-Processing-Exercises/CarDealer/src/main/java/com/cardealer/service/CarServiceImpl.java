package com.cardealer.service;

import com.cardealer.constant.GlobalConstants;
import com.cardealer.model.dto.query2.CarViewDto;
import com.cardealer.model.dto.query2.CarViewRootDto;
import com.cardealer.model.dto.query4.CarAndPartsViewDto;
import com.cardealer.model.dto.query4.CarAndPartsViewRootDto;
import com.cardealer.model.dto.query6.CarWithOutIdViewDto;
import com.cardealer.model.dto.query6.SalesViewDto;
import com.cardealer.model.dto.query6.SalesViewRootDto;
import com.cardealer.model.dto.seed.CarSeedDto;
import com.cardealer.model.entity.Car;
import com.cardealer.repository.CarRepository;
import com.cardealer.util.RandomUtil;
import com.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartService partService;
    private final RandomUtil randomUtil;


    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, PartService partService, RandomUtil randomUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partService = partService;
        this.randomUtil = randomUtil;
    }

    @Override
    public void seedCars(List<CarSeedDto> carSeedDtos) {
        carSeedDtos
                .forEach(carSeedDto -> {
                    if(this.validationUtil.isValid(carSeedDto)){
                        if(this.carRepository
                                .findByMakeAndModelAndTravelledDistance(carSeedDto.getMake(),
                                carSeedDto.getModel(), carSeedDto.getTravelledDistance()) == null){

                            Car car = this.modelMapper.map(carSeedDto, Car.class);
                            car.setParts(this.partService.getRandomParts());

                            this.carRepository.saveAndFlush(car);

                        }else {
                            System.out.println("Already in DB");
                        }

                    }else {
                        this.validationUtil.printViolations(carSeedDto);
                    }
                });
    }

    @Override
    public Car getRandomCar() {
        long randomId = this.randomUtil.randomId(this.carRepository.count());
        return this.carRepository.getOne(randomId);
    }

    @Override
    public CarViewRootDto query2CarsFromMakeToyota() {

        List<CarViewDto> dtos = carRepository
                .findAllByMakeLikeOrderByModelAscTravelledDistanceDesc(
                        GlobalConstants.QUERY2_CAR_MAKE
                )
                .stream()
                .map(obj -> modelMapper.map(obj, CarViewDto.class))
                .collect(Collectors.toList());

        return new CarViewRootDto(dtos);
    }

    @Override
    public CarAndPartsViewRootDto query4CarsWithTheirListOfParts() {
        List<CarAndPartsViewDto> dtos =
                carRepository.findAll()
                .stream()
                .map(obj -> modelMapper.map(obj, CarAndPartsViewDto.class))
                .collect(Collectors.toList());

        return new CarAndPartsViewRootDto(dtos);
    }
}
