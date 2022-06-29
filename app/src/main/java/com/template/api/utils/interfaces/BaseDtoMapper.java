package com.template.api.utils.interfaces;

import com.template.api.apps.banks.domain.Sales;
import com.template.api.apps.banks.dto.BankDto;
import org.checkerframework.checker.units.qual.A;

public interface BaseDtoMapper<E, R, C , D> {

    R toResponse(E entity);

    E create(C dto);

    D create(A dto);

    Sales update(BankDto.SaleResponse update);
}
