package com.cardealer;

import com.cardealer.constant.GlobalConstants;
import com.cardealer.model.dto.query2.CarViewRootDto;
import com.cardealer.model.dto.query3.SupplierAndPartsCountRootViewDto;
import com.cardealer.model.dto.query4.CarAndPartsViewRootDto;
import com.cardealer.model.dto.query5.CustomerSalesViewRootDto;
import com.cardealer.model.dto.query6.SalesViewRootDto;
import com.cardealer.model.dto.seed.CarSeedRootDto;
import com.cardealer.model.dto.seed.CustomerSeedRootDto;
import com.cardealer.model.dto.seed.PartSeedRootDto;
import com.cardealer.model.dto.seed.SupplierSeedRootDto;
import com.cardealer.model.dto.query1.CustomerViewRootDto;
import com.cardealer.service.*;
import com.cardealer.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static com.cardealer.constant.GlobalConstants.*;

@Component
public class AppController implements CommandLineRunner {

    private final XmlParser xmlParser;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public AppController(XmlParser xmlParser, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.xmlParser = xmlParser;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args){

        System.out.println("Start Car Dealer application.");
        try {
            this.seedDatabaseFromXmlFiles();
            this.exQuery1();
            this.exQuery2();
            this.exQuery3();
            this.exQuery4();
            this.exQuery5();
            this.exQuery6();

        }catch (IOException | JAXBException e){
            e.printStackTrace();
        }
    }

    private void exQuery1() throws JAXBException, IOException {
        CustomerViewRootDto customerViewRootDto =
                customerService.getAllOrderedCustomers();

        xmlParser.marshalToFile(customerViewRootDto, FILE_QUERY1);
        System.out.println(String.format("query: %s", FILE_QUERY1));
    }

    private void exQuery2() throws IOException, JAXBException {
        CarViewRootDto dto =
                carService.query2CarsFromMakeToyota();

        xmlParser.marshalToFile(dto, FILE_QUERY2);
        System.out.println(String.format("query: %s", FILE_QUERY2));
    }

    private void exQuery3() throws IOException, JAXBException {
        SupplierAndPartsCountRootViewDto dto =
                supplierService.query3LocalSuppliers();

        xmlParser.marshalToFile(dto, FILE_QUERY3);
        System.out.println(String.format("query: %s", FILE_QUERY3));
    }

    private void exQuery4() throws IOException, JAXBException {
        CarAndPartsViewRootDto dto =
                carService.query4CarsWithTheirListOfParts();

        xmlParser.marshalToFile(dto, FILE_QUERY4);
        System.out.println(String.format("query: %s", FILE_QUERY4));
    }

    private void exQuery5() throws IOException, JAXBException {
        CustomerSalesViewRootDto dto = customerService.query5TotalSalesByCustomer();

        xmlParser.marshalToFile(dto, FILE_QUERY5);
        System.out.println(String.format("query: %s", FILE_QUERY5));
    }

    private void exQuery6() throws IOException, JAXBException {
        SalesViewRootDto dto = saleService.query6SalesWithAppliedDiscount();

        xmlParser.marshalToFile(dto, FILE_QUERY6);
        System.out.println(String.format("query: %s", FILE_QUERY6));
    }

    private void seedDatabaseFromXmlFiles() throws IOException, JAXBException {
        if(!supplierService.isEmpty()){
            System.out.println("Found database.");
            return;
        }
        System.out.println("Seed database from xml files...");

        // seedSuppliers
        SupplierSeedRootDto supplierSeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.SUPPLIERS_FILE_PATH, SupplierSeedRootDto.class);
        this.supplierService.seedSuppliers(supplierSeedRootDto.getSuppliers());

        // seedParts
        PartSeedRootDto partSeedRootDto = this.xmlParser
                .unmarshalFromFile(PARTS_FILE_PATH, PartSeedRootDto.class);
        this.partService.seedParts(partSeedRootDto.getParts());

        // seedCars
        CarSeedRootDto carSeedRootDto = this.xmlParser
                .unmarshalFromFile(CARS_FILE_PATH, CarSeedRootDto.class);
        this.carService.seedCars(carSeedRootDto.getCars());

        // seedCustomers
        CustomerSeedRootDto customerSeedRootDto = this.xmlParser
                .unmarshalFromFile(CUSTOMERS_FILE_PATH, CustomerSeedRootDto.class);
        this.customerService.seedCustomers(customerSeedRootDto.getCustomers());

        // seedSales
        this.saleService.seedSales();

        System.out.println("Seed Done.");
    }
}
