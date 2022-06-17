package com.template.api.apps.bank.service;

import com.template.api.apps.bank.dto.BankDto;
import com.template.api.utils.dtos.PagableDto;

import java.util.List;


public interface BankService {

    PagableDto.Response<BankDto.Response>getBanksName(BankDto.Request request);
    List<BankDto.Response> getBanks(BankDto.Request request);
    void createBankName(BankDto.Create create);

    void deleteBank(long id);

}
