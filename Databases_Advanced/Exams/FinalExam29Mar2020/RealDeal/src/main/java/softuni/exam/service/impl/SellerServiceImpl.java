package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constant.Constants;
import softuni.exam.models.dtos.SellerSeedDto;
import softuni.exam.models.dtos.SellerSeedRootDto;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static softuni.exam.constant.Constants.*;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public SellerServiceImpl(SellerRepository repository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return repository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return FileUtil.read(SELLERS_FILE_PATH);
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder result = new StringBuilder();

        SellerSeedRootDto rootDto = xmlParser
                .unmarshalFromFile(SELLERS_FILE_PATH, SellerSeedRootDto.class);

        if (rootDto == null) {
            return NOT_FOUND;
        }

        Collection<SellerSeedDto> dtos = rootDto.getSellers();


        if (dtos == null || dtos.size() == 0) {
            return NOT_FOUND;
        }

        for (SellerSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)) {

                if (repository.findByEmail(dto.getEmail())
                        .isEmpty()) {

                    Seller seller = mapper.map(dto, Seller.class);
                    repository.saveAndFlush(seller);

                    result.append(String.format(SUCCESSFUL_IMPORT_SELLER,
                            seller.getLastName(),
                            seller.getEmail())
                    );

                } else {
                    result.append(EXISTS);
                }

            } else {
                result.append(String.format(INCORRECT_DATA_MESSAGE, "seller"));
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public Optional<Seller> getById(Long id) {
        return repository.findById(id);
    }
}
