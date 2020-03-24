package com.cardealer.service;

import com.cardealer.model.dto.query5.CustomerSalesViewRootDto;
import com.cardealer.model.dto.seed.CustomerSeedDto;
import com.cardealer.model.dto.query1.CustomerViewRootDto;
import com.cardealer.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    void seedCustomers(List<CustomerSeedDto> customerSeedDtos);

    Customer getRandomCustomer();

    CustomerViewRootDto getAllOrderedCustomers();

    CustomerSalesViewRootDto query5TotalSalesByCustomer();
}
