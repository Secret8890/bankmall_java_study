package com.template.api.apps.Account.service;

import com.template.api.apps.Account.dto.AccountDto;
import com.template.api.utils.dtos.PagableDto;

public interface AccountService {
    PagableDto.Response<AccountDto.Response> getPageAccounts(AccountDto.Request request);

    void createAccount(AccountDto.Create create);

    void updateAccount(Long id, AccountDto.Update update);
}
