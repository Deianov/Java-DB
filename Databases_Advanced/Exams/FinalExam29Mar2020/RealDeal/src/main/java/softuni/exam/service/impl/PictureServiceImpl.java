package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PictureSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.JsonParser;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

import static softuni.exam.constant.Constants.*;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final JsonParser jsonParser;
    private final CarService carService;

    @Autowired
    public PictureServiceImpl(PictureRepository repository, ModelMapper mapper, ValidationUtil validationUtil, JsonParser jsonParser, CarService carService) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.jsonParser = jsonParser;
        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return repository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return FileUtil.read(PICTURES_FILE_PATH);
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder result = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT);

        PictureSeedDto[] dtos = jsonParser
                .objectFromFile(PICTURES_FILE_PATH, PictureSeedDto[].class);

        if (dtos == null || dtos.length == 0) {
            return NOT_FOUND;
        }

        for (PictureSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)) {

                Car car = carService.getById(dto.getCar()).orElse(null);

                if (car != null && getByName(dto.getName())
                        .isEmpty()) {
                    Picture picture = mapper.map(dto, Picture.class);
                    picture.setDateAndTime(LocalDateTime.parse(dto.getDateAndTime(), formatter));
                    picture.setCar(car);
                    repository.saveAndFlush(picture);


                    result.append(String.format(SUCCESSFUL_IMPORT_PICTURE,
                            "picture",
                            picture.getName())
                    );

                } else {
                    result.append(EXISTS);
                }

            } else {
                result.append(String.format(INCORRECT_DATA_MESSAGE, "picture"));
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public Optional<Picture> getByName(String name) {
        return repository.findByName(name);
    }
}
