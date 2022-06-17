package com.template.api.apps.Bank.dto;

import com.template.api.apps.Bank.domain.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankDtoMapper {

    BankDtoMapper INSTANCE = Mappers.getMapper(BankDtoMapper.class);
    BankDto.Response toResponse(Bank Banks);
    Bank create(BankDto.Create create);

}
