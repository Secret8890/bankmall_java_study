package com.template.api.apps.Bank.service;

import com.template.api.apps.Bank.dto.BankDto;
import com.template.api.utils.dtos.PagableDto;

import java.util.List;


public interface BankService {

    PagableDto.Response<BankDto.Response>getPageBanks(BankDto.Request request);
    List<BankDto.Response> getBanks(BankDto.Request request);
    void createBankName(BankDto.Create create);

}
