package softuni.exam.service;



import softuni.exam.models.entities.Car;

import java.io.IOException;
import java.util.Optional;

public interface CarService {

    boolean areImported();

    String readCarsFileContent() throws IOException;
	
	String importCars() throws IOException;

    String getCarsOrderByPicturesCountThenByMake();

    Optional<Car> getByMakeAndModelAndKilometers(String make, String model, Integer kilometers);
    Optional<Car> getById(Long id);
}
