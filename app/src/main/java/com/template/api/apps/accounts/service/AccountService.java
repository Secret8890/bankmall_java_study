package com.template.api.apps.accounts.service;

import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.utils.dtos.PagableDto;

import java.util.List;

public interface AccountService {

    PagableDto.Response<AccountDto.Response> getPageAccounts(AccountDto.Request request);
    List<AccountDto.Response> getAccounts(AccountDto.Request request);
    AccountDto.Response getAccount(long id);
    AccountDto.Response createAccount(AccountDto.Create create);
    AccountDto.Response updateAccount(long id, AccountDto.Update update);
    AccountDto.Response deleteAccount(long id);
}
