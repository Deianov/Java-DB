package hiberspring.service;

import hiberspring.domain.entities.Product;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public interface ProductService {
    Optional<Product> getByName(String name);

    Boolean productsAreImported();

    String readProductsXmlFile() throws IOException;

    String importProducts() throws JAXBException, FileNotFoundException;
}
