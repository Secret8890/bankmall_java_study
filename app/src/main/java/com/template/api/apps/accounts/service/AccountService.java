package com.template.api.apps.accounts.service;

import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.utils.dtos.PagableDto;
import javassist.NotFoundException;

import java.util.List;

public interface AccountService {

    PagableDto.Response<AccountDto.Response> getPageAccounts(AccountDto.Request request);
    List<AccountDto.Response> getAccounts(AccountDto.Request request);
    AccountDto.Response getAccount(long id);

    List<AccountDto.Response> getAccount(String keyword);
    void createAccount(AccountDto.Create create);
    AccountDto.Response deleteAccount(long id);

    void updateAccount(Long id,AccountDto.Update update) throws NotFoundException;

    AccountDto.Update updateAccount(long id);

}

