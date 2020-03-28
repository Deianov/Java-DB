package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class XmlParserImpl implements XmlParser {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unmarshalFromFile(String filePath, Class<T> tClass) throws IOException, JAXBException {
        try (final InputStream inputStream = new FileInputStream(filePath))
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (T) unmarshaller.unmarshal(inputStream);
        }
    }

    @Override
    public <T> void marshalToFile(T obj, String path) throws IOException, JAXBException {
        try (final OutputStream outputStream = new FileOutputStream(path))
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.displayName());
            marshaller.marshal(obj, outputStream);
        }
    }
}
