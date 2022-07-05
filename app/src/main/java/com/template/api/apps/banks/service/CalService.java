//package com.template.api.apps.banks.service;
//
//import com.template.api.apps.banks.domain.Bank;
//import com.template.api.apps.banks.dto.BankDto;
//import com.template.api.apps.banks.dto.BankDtoMapper;
//import com.template.api.jpa.base.BaseRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//@Repository
//@Service
//
//public interface CalService extends BaseRepository<Bank> {
//
//    public CalService(BankDto.CalResponce calResponce) throws NullPointerException{
//        if(calResponce.getBaseRate() >= 0 ){
//            calResponce.setRate(calResponce.getBaseRate()+ calResponce.getAddRate()-calResponce.getDiscountRate());
//            Bank bank = BankDtoMapper.INSTANCE.calRate(calResponce);
//
//            BankDto.Create create = BankDtoMapper.INSTANCE.create(create);
//
//            return calResponce.setRate(bank);
//
//        } else {
//            throw new NullPointerException("값을 정확히 입력하세요");
//        }
//        if(response.getLoanMoney()>=0) {
//            response.setReturnMoney((long) ((response.getLoanMoney() * response.getRate() / 12) + (response.getLoanMoney() / (response.getReturnYear()) * 12)));
//        }else {
//            throw new NullPointerException("값을 정확히 입력하세요...");
//        }
//    }
// }
