package softuni.exam.service;


import softuni.exam.models.entities.Picture;

import java.io.IOException;
import java.util.Optional;

public interface PictureService {

    boolean areImported();

    String readPicturesFromFile() throws IOException;
	
	String importPictures() throws IOException;

    Optional<Picture> getByName(String name);
}
