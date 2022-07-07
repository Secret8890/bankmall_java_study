package com.template.api.apps.Account.service;

import com.template.api.apps.Account.dto.AccountDto;
import com.template.api.utils.dtos.PagableDto;

import java.util.List;

public interface AccountService {
    PagableDto.Response<AccountDto.Response> getPageAccounts(AccountDto.Request request);
    List<AccountDto.Response>getAccounts(AccountDto.Request request);
    AccountDto.Response getAccount(long id);
    AccountDto.Response updateAccount(long id, AccountDto.Update update);
    AccountDto.Update updateAccount(long id);

    void createAccount(AccountDto.Create create);

    void updateAccount(Long id, AccountDto.Update update);


    void deleteAccount(Long id);
}
