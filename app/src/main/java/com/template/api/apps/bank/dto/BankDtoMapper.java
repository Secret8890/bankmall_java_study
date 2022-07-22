package com.template.api.apps.bank.dto;

import com.template.api.apps.bank.domain.Bank;
import com.template.api.apps.bank.domain.Sales;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BankDtoMapper extends BaseDtoMapper
     //   <Bank, BankDto.Response, BankDto.Create, BankDto.SaleCreate>
{

    BankDtoMapper INSTANCE = Mappers.getMapper(BankDtoMapper.class);
    BankDto.Response toResponse(Bank Banks);
    BankDto.SaleResponse toResponse(Sales sales);
    //List<BankDto.Response> toResponses(List<Bank> banks);
    Bank create(BankDto.Create create);
    BankDto.Create create(BankDto.Response response);

    Sales createSales (BankDto.SaleCreate item);

    void update(BankDto.Update update, @MappingTarget Bank bank);

    Sales update(BankDto.SaleUpdate item);



    // List<BankDto.CalcRequest> calcRequests(List<Bank> banks);
}
