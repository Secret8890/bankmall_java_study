package com.template.api.apps.accounts.service;

import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.utils.dtos.PagableDto;

import java.util.List;

public interface AccountService {

    PagableDto.Response<AccountDto.Response> getPageAccounts(AccountDto.Request request);
    List<AccountDto.Response> getAccounts(AccountDto.Request request);
    AccountDto.Response getAccount(long id);
    void createAccount(AccountDto.Create create);
    AccountDto.Response deleteAccount(long id);

    void updateAccount(AccountDto.Update update);

    AccountDto.Update updateAccount(long id);

}

