package com.template.api.apps.Account.service;

import com.template.api.apps.Account.domain.Account;
import com.template.api.apps.Account.domain.AccountRepository;
import com.template.api.apps.Account.dto.AccountDto;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Primary
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Qualifier("AccountServiceImpl")

public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Override
    public PagableDto.Response<AccountDto.Response> getPageAccounts(AccountDto.Request request) {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if (StringUtils.isNotBlank(request.getSort())){
            sort =Sort.by(request.getOrder().dir, request.getSort());
        }

        Restrictions r = new Restrictions();
        r.eq("isActive", true);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);
        Page<Account> items = accountRepository.findAll(r.output(), pageRequest);
        PagableDto.Response<AccountDto.Response> pages = PagableDto.Response.of(items);

        return  pages;
    }

    @Override
    public void createAccount(AccountDto.Create create) {

    }

    @Override
    public void updateAccount(Long id, AccountDto.Update update) {

    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }


}
