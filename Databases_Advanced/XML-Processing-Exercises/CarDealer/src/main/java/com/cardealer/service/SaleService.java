package com.cardealer.service;

import com.cardealer.model.dto.query6.SalesViewRootDto;

public interface SaleService {
    void  seedSales();
    SalesViewRootDto query6SalesWithAppliedDiscount();
}
