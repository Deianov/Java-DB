package softuni.workshop.util;


import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface XmlParser {
    <T> T unmarshalFromFile(String path, Class<T> tClass);

    <T> void marshalToFile(T obj, String path);
}
