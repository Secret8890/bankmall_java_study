package com.template.api.apps.bank.service;


import com.template.api.apps.bank.domain.Bank;
import com.template.api.apps.bank.domain.BankRepository;
import com.template.api.apps.bank.dto.BankDto;
import com.template.api.apps.bank.dto.BankDtoMapper;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Primary
@Qualifier("BankServiceImpl")

public class BankServiceImpl {

    private final BankRepository bankRepository;

    public PagableDto.Response<BankDto.Response> getPageBanks(BankDto.Request request){return null;}
    public List<BankDto.Response> getBanks(BankDto.Request request){ return null;}

    @Transactional
    public void createBankName(BankDto.Create create){
        Bank bank = BankDtoMapper.INSTANCE.create(create);
        bankRepository.save(bank);
    }

    public List<BankDto.Response>getBanksName(){
        List<Bank> bank = bankRepository.findAll();
        List<BankDto.Response>responses = bank.stream().map(Bank::toResponse).collect(Collectors.toList());
        return responses;
    }

    @Transactional
    public void deleteBank(Long id){
        bankRepository.deleteById(id);
    }

}
