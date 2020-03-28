package softuni.exam.util;


import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface XmlParser {
    <T> T unmarshalFromFile(String path, Class<T> tClass) throws IOException, JAXBException;

    <T> void marshalToFile(T obj, String path) throws IOException, JAXBException;
}
