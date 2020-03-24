package com.cardealer.service;

import com.cardealer.model.dto.query5.CustomerSalesViewDto;
import com.cardealer.model.dto.query5.CustomerSalesViewRootDto;
import com.cardealer.model.dto.seed.CustomerSeedDto;
import com.cardealer.model.dto.query1.CustomerViewDto;
import com.cardealer.model.dto.query1.CustomerViewRootDto;
import com.cardealer.model.entity.Customer;
import com.cardealer.model.entity.Part;
import com.cardealer.model.entity.Sale;
import com.cardealer.repository.CustomerRepository;
import com.cardealer.util.RandomUtil;
import com.cardealer.util.ValidationUtil;
import org.apache.logging.log4j.util.PropertySource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final RandomUtil randomUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, RandomUtil randomUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.randomUtil = randomUtil;
    }

    @Override
    public void seedCustomers(List<CustomerSeedDto> customerSeedDtos) {
        customerSeedDtos
                .forEach(customerSeedDto -> {
                    if (this.validationUtil.isValid(customerSeedDto)) {
                        if (this.customerRepository
                                .findByNameAndBirthDate(customerSeedDto.getName(),
                                        customerSeedDto.getBirthDate()) == null) {

                            Customer customer = this.modelMapper
                                    .map(customerSeedDto, Customer.class);

                            this.customerRepository.saveAndFlush(customer);

                        } else {
                            System.out.println("Already in DB");
                        }
                    } else {
                        this.validationUtil.printViolations(customerSeedDto);
                    }
                });
    }

    @Override
    public Customer getRandomCustomer() {
        long randomId = this.randomUtil.randomId(this.customerRepository.count());
        return this.customerRepository.getOne(randomId);
    }

    @Override
    public CustomerViewRootDto getAllOrderedCustomers() {
        CustomerViewRootDto customerViewRootDto = new CustomerViewRootDto();

        List<CustomerViewDto> customerViewDtos = this.customerRepository
                .findByAllByBirthDateAndIsYoungDriver()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerViewDto.class))
                .collect(Collectors.toList());

        customerViewRootDto.setCustomers(customerViewDtos);

        return customerViewRootDto;
    }

    @Override
    public CustomerSalesViewRootDto query5TotalSalesByCustomer() {

        List<CustomerSalesViewDto> dtos = new ArrayList<>();

        for (Customer customer : customerRepository.findCustomersWithSales()) {

            List<Sale> sales = customer.getSales();

            BigDecimal spendMoney = sales
                    .stream()
                    .map(sale ->{
                        double discount = 1 - sale.getDiscount();

                        BigDecimal partsPrice = sale.getCar().getParts()
                                .stream()
                                .map(Part::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                        return partsPrice.multiply(BigDecimal.valueOf(discount));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            dtos.add(new CustomerSalesViewDto(customer.getName(), sales.size(), spendMoney));
        }

        dtos = dtos
            .stream()
                .sorted((c1, c2) -> {
                    double compare = Double.compare(c2.getSpentMoney().doubleValue(), c1.getSpentMoney().doubleValue());
                    if(compare == 0){
                        return Integer.compare(c2.getBoughtCars(), c1.getBoughtCars());
                    }
                    return compare == 0 ? 0 : compare > 0 ? 1 : -1;
                })
                .collect(Collectors.toList());

        return
                new CustomerSalesViewRootDto(dtos);
    }
}
