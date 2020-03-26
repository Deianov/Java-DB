package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.PictureSeedDto;
import softuni.exam.domain.dto.PictureSeedRootDto;
import softuni.exam.domain.entity.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;


import javax.xml.bind.JAXBException;
import java.io.IOException;

import static softuni.exam.constant.GlobalConstants.*;


@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importPictures() throws IOException, JAXBException {
        StringBuilder result = new StringBuilder();

        PictureSeedRootDto rootDto = xmlParser
                .unmarshalFromFile(FILE_PATH_PICTURES, PictureSeedRootDto.class);

        for (PictureSeedDto dto : rootDto.getPictures()) {

            if (validationUtil.isValid(dto)){

                if(pictureRepository.findPictureByUrl(dto.getUrl()).isPresent()){
                    result.append(EXISTS);
                }else {
                    Picture picture = modelMapper.map(dto, Picture.class);
                    pictureRepository.saveAndFlush(picture);
                    result.append(String.format(IMPORTED_PICTURE, picture.getUrl()));
                }

            }else {
                result.append(INVALID_PICTURE);
            }
            result.append(System.lineSeparator());
        }
       return result.toString();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return FileUtil.read(FILE_PATH_PICTURES);
    }

    @Override
    public Picture getPictureByUrl(String url) {
        return pictureRepository.findPictureByUrl(url).orElse(null);
    }
}
