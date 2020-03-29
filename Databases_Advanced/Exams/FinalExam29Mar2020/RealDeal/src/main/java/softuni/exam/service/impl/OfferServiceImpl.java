package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.OfferSeedDto;
import softuni.exam.models.dtos.OfferSeedRootDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.PictureService;
import softuni.exam.service.SellerService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Collection;

import static softuni.exam.constant.Constants.*;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CarService carService;
    private final SellerService sellerService;
    private final PictureService pictureService;

    @Autowired
    public OfferServiceImpl(OfferRepository repository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, CarService carService, SellerService sellerService, PictureService pictureService) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.carService = carService;
        this.sellerService = sellerService;
        this.pictureService = pictureService;
    }

    @Override
    public boolean areImported() {
        return repository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return FileUtil.read(OFFERS_FILE_PATH);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder result = new StringBuilder();

        OfferSeedRootDto rootDto = xmlParser
                .unmarshalFromFile(OFFERS_FILE_PATH, OfferSeedRootDto.class);

        if (rootDto == null) {
            return NOT_FOUND;
        }

        Collection<OfferSeedDto> dtos = rootDto.getOffers();


        if (dtos == null || dtos.size() == 0) {
            return NOT_FOUND;
        }

        for (OfferSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)) {

                if (repository.findByDescriptionAndAddedOn(dto.getDescription(), dto.getAddedOn())
                        .isEmpty()) {

                    Offer offer = mapper.map(dto, Offer.class);

                    Car car = carService.getById(dto.getCar().getId()).orElse(null);
                    Seller seller = sellerService.getById(dto.getSeller().getId()).orElse(null);
                    offer.setCar(car);
                    offer.setSeller(seller);
                    offer.setPictures(pictureService.getPicturesByCar(car));

                    repository.saveAndFlush(offer);

                    result.append(String.format(SUCCESSFUL_IMPORT_OFFER,
                            offer.getAddedOn(),
                            offer.isHasGoldStatus())
                    );

                } else {
                    result.append(EXISTS);
                }

            } else {
                result.append(String.format(INCORRECT_DATA_MESSAGE, "offer"));
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }
}
