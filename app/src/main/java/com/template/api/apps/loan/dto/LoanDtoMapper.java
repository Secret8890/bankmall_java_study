//package com.template.api.apps.loan.dto;
//
//import com.template.api.apps.loan.domain.Loan;
//import com.template.api.utils.interfaces.BaseDtoMapper;
//import org.mapstruct.Mapper;
//import org.mapstruct.MappingTarget;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//
//@Mapper
//public interface LoanDtoMapper extends BaseDtoMapper<Loan, LoanDto.Response, LoanDto.Create, LoanDto.Update> {
//        LoanDtoMapper INSTANCE = Mappers.getMapper(LoanDtoMapper.class);
//
//        List<LoanDto.Response> toResponse(List<Loan> loans);
//
//        Loan create(LoanDto.Create create);
//
//        void update(LoanDto.Update update, @MappingTarget Loan loan);
//}
