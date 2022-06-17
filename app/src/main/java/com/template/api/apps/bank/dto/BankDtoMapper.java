package com.template.api.apps.bank.dto;

import com.template.api.apps.bank.domain.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BankDtoMapper {

    BankDtoMapper INSTANCE = Mappers.getMapper(BankDtoMapper.class);
    BankDto.Response toResponse(Bank Banks);

    List<BankDto.Response> toResponses(List<Bank> banks);
    Bank create(BankDto.Create create);

}
