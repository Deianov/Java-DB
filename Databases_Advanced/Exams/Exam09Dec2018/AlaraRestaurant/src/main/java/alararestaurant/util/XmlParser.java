package alararestaurant.util;


import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface XmlParser {
    <T> T unmarshalFromFile(String path, Class<T> tClass) throws FileNotFoundException, JAXBException;

    <T> void marshalToFile(T obj, String path) throws FileNotFoundException, JAXBException;
}
