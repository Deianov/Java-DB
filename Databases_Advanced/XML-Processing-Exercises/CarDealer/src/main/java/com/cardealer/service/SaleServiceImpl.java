package com.cardealer.service;

import com.cardealer.model.dto.query6.CarWithOutIdViewDto;
import com.cardealer.model.dto.query6.SalesViewDto;
import com.cardealer.model.dto.query6.SalesViewRootDto;
import com.cardealer.model.entity.Car;
import com.cardealer.model.entity.Part;
import com.cardealer.model.entity.Sale;
import com.cardealer.repository.SaleRepository;
import com.cardealer.util.RandomUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final RandomUtil randomUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, RandomUtil randomUtil, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.randomUtil = randomUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSales() {
        for (int i = 0; i < 20; i++) {
            Sale sale = new Sale();

            sale.setDiscount(this.setRandomDiscount());
            sale.setCar(this.carService.getRandomCar());
            sale.setCustomers(this.customerService.getRandomCustomer());

            this.saleRepository.saveAndFlush(sale);
        }
    }

    private Double setRandomDiscount() {
        Double[] discounts =
                new Double[]{0D, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5};

        return discounts[this.randomUtil.randomIndex(discounts.length)];
    }

    @Override
    public SalesViewRootDto query6SalesWithAppliedDiscount() {

        List<SalesViewDto> dtos = new ArrayList<>();

        for (Sale sale : saleRepository.findAll()) {

            SalesViewDto saleDto = new SalesViewDto();
            Car car = sale.getCar();

            CarWithOutIdViewDto carDto = modelMapper.map(car, CarWithOutIdViewDto.class);
            saleDto.setCar(carDto);
            saleDto.setCustomerName(sale.getCustomers().getName());

            Double discount = sale.getDiscount();
            discount = discount != null && discount == 0 ? null : discount;
            saleDto.setDiscount(discount);

            BigDecimal carPrice = car.getParts()
                    .stream()
                    .map(Part::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            saleDto.setPrice(carPrice);
            saleDto.setPriceWithDiscount(discount == null ? null : carPrice.multiply(BigDecimal.valueOf(1 - discount)));

            dtos.add(saleDto);
        }

        return new SalesViewRootDto(dtos);
    }
}
