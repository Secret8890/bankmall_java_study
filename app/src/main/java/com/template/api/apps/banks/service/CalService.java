//package com.template.api.apps.banks.service;
//
//import com.template.api.apps.banks.domain.Bank;
//import com.template.api.apps.banks.dto.BankDto;
//import com.template.api.apps.banks.dto.BankDtoMapper;
//import com.template.api.jpa.base.BaseRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Repository
//@Service
//
//public interface CalService extends BaseRepository<Bank> {
//    @Transactional
//
//    public default void responseBank(BankDto.Response response) throws NullPointerException{
//        if(response.getBaseRate() >= 0 ){
//            response.setRate(response.getBaseRate()+ response.getAddRate()-response.getRateByUseMethodMax());
//            Bank bank = BankDtoMapper.INSTANCE.calRate(response);
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
//}
