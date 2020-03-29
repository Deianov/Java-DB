package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constant.Constants;
import softuni.exam.models.dtos.CarSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.JsonParser;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static softuni.exam.constant.Constants.*;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final JsonParser jsonParser;

    @Autowired
    public CarServiceImpl(CarRepository repository, ModelMapper mapper, ValidationUtil validationUtil, JsonParser jsonParser) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.jsonParser = jsonParser;
    }


    @Override
    public boolean areImported() {
        return repository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return FileUtil.read(CARS_FILE_PATH);
    }

    @Override
    public String importCars() throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT);


        StringBuilder result = new StringBuilder();

        CarSeedDto[] dtos = jsonParser
                .objectFromFile(CARS_FILE_PATH, CarSeedDto[].class);

        if (dtos == null || dtos.length == 0) {
            return NOT_FOUND;
        }

        for (CarSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)) {

                if (getByMakeAndModelAndKilometers(dto.getMake(), dto.getModel(), dto.getKilometers())
                        .isEmpty()) {

                    Car car = mapper.map(dto, Car.class);
                    car.setRegisteredOn(LocalDate.parse(dto.getRegisteredOn(), formatter));

                    repository.saveAndFlush(car);

                    result.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                            "car",
                            car.getMake(),
                            car.getModel())
                    );

                } else {
                    result.append(EXISTS);
                }

            } else {
                result.append(String.format(INCORRECT_DATA_MESSAGE, "car"));
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {

        StringBuilder result = new StringBuilder();

        List<Car> cars = repository.findAllSorted();

        for (Car car : cars) {

            String carInfo = String.format("Car make - %s, model - %s\n" +
                    "\tKilometers - %d\n" +
                    "\tRegistered on - %s\n" +
                    "\tNumber of pictures - %d\n",
                    car.getMake(),
                    car.getModel(),
                    car.getKilometers(),
                    car.getRegisteredOn(),
                    car.getPictures().size()
            );

            result.append(carInfo).append(System.lineSeparator());
        }

        return result.toString();
    }

    @Override
    public Optional<Car> getByMakeAndModelAndKilometers(String make, String model, Integer kilometers) {
        return repository.findByMakeAndModelAndKilometers(make, model, kilometers);
    }

    @Override
    public Optional<Car> getById(Long id) {
        return repository.findById(id);
    }
}
