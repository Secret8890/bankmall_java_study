package com.template.api.apps.banks.dto;

import com.template.api.apps.banks.domain.Bank;
import com.template.api.apps.banks.domain.Sales;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankDtoMapper extends BaseDtoMapper<Bank, BankDto.Response, BankDto.Create, BankDto.SaleCreate> {
    BankDtoMapper INSTANCE = Mappers.getMapper(BankDtoMapper.class);

    Bank create(BankDto.Create create);

    BankDto.SaleResponse toResponse(Sales sales);

    BankDto.Response toResponse(Bank bank);

    void update(BankDto.Update update, @MappingTarget Bank bank);

    Sales createSales(BankDto.SaleCreate item);

    BankDto.Create create(BankDto.Response response);

//    Bank calRate(BankDto.Response response);

//    Bank CalResponce(BankDto.Request request);

//    Bank CalRate(BankDto.Create create);
//    BankDto.CalResponce create(Bank bank);

}
