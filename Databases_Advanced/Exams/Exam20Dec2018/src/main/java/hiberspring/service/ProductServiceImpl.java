package hiberspring.service;

import hiberspring.constant.GlobalConstants;
import hiberspring.domain.dtos.ProductSeedDto;
import hiberspring.domain.dtos.ProductSeedRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.ProductRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static hiberspring.constant.GlobalConstants.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final BranchService branchService;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, BranchService branchService) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.branchService = branchService;
    }


    @Override
    public Optional<Product> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Boolean productsAreImported() {
        return repository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return FileUtil.read(GlobalConstants.FILE_PRODUCTS);
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        StringBuilder result = new StringBuilder();

        ProductSeedRootDto rootDto = xmlParser
                .parseXml(ProductSeedRootDto.class, FILE_PRODUCTS);

        if(rootDto == null){
            return (NOT_FOUND);
        }

        List<ProductSeedDto> dtos = rootDto.getProducts();

        if(dtos == null || dtos.size() == 0){
            return (NOT_FOUND);
        }

        for (ProductSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                if(getByName(dto.getName()).isEmpty()){

                    Product product = mapper.map(dto, Product.class);

                    Branch branch = branchService.getByName(dto.getBranchName()).orElse(null);

                    if(branch != null){

                        product.setBranch(branch);
                        repository.saveAndFlush(product);

                        result.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                                "Product",
                                product.getName())
                        );

                    } else {
                        result.append(INCORRECT_DATA_MESSAGE);
                    }
                } else {
                    result.append(EXISTS);
                }
            }else {
                result.append(INCORRECT_DATA_MESSAGE);
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }
}
