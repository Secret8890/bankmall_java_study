package com.template.api.apps.banks.dto;

import com.template.api.apps.banks.domain.Bank;
import com.template.api.apps.banks.domain.Sales;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BankDtoMapper extends BaseDtoMapper<Bank , BankDto.Response, BankDto.Create , BankDto.SaleCreate> {

   BankDtoMapper INSTANCE = Mappers.getMapper(BankDtoMapper.class);

   List<BankDto.Response> toResponses(List<Bank> banks);

   Bank create(BankDto.Create create);

   Bank calRate(BankDto.Response response);

   Sales create(BankDto.SaleCreate salecreate);

//   List<BankDto.SaleResponse> toResponse(BankDto.SaleResponse saleResponse);
}
