package com.template.api.apps.banks.dto;

import com.template.api.apps.banks.domain.Bank;
import com.template.api.apps.banks.domain.Sales;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BankDtoMapper extends BaseDtoMapper<Bank , BankDto.Response, BankDto.Create , BankDto.SaleCreate>

{
   BankDtoMapper INSTANCE = Mappers.getMapper(BankDtoMapper.class);

   List<BankDto.Response> toResponses(List<Bank> banks);

   Bank create(BankDto.Create create);

   Bank calRate(BankDto.Response response);

//   List<BankDto.Response> toResponses(List<Bank> banks);
////   BankDto.Response toResponse(Bank bank);
//
//   Bank create(BankDto.Create create);
//
//   Bank detail(BankDto.Detail detail);
//   Bank salelist(BankDto.SaleCreate create);
//   Bank calRate(BankDto.Response response);
//
   Sales createSales(BankDto.SaleCreate salecreate);

//   List<BankDto.SaleCreate> save(List<Sales> sales);
//
  BankDto.SaleResponse toResponse(Sales sales);
   BankDto.Response toResponse(Bank bank);

    void update(BankDto.Update update,@MappingTarget Bank bank);

//   BankDto.Update Update(@MappingTarget long id, BankDto.Update update) ;

//   BankDto.Update update (@MappingTarget BankDto.Update update, Bank bank);

//    BankDto.Update Update(BankDto.Update update);

//   BankDto.Request toResponse(BankDto.Request request);



//  void toResponse(PagableDto.Response<BankDto.Response> banks);

//


//   List<BankDto.SaleResponse> toResponse(BankDto.SaleResponse saleResponse);
}
