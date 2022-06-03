package com.template.api.apps.accounts.service;

import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.accounts.domain.AccountRepository;
import com.template.api.apps.accounts.dto.AccountDto;
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

import java.util.List;

/**
 * JPA
 * 간결한 CRUD 시 사용
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Primary
@Qualifier("AccountServiceImpl")
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    /**
     * 사용자 목록 조회 (Paging)
     */
    @Override
    public PagableDto.Response<AccountDto.Response> getPageAccounts(AccountDto.Request request) {
        //region 정렬
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if(StringUtils.isNotBlank(request.getSort())){
            sort = Sort.by(request.getOrder().dir, request.getSort());
        }
        //endreigon

        //region 조건
        Restrictions r = new Restrictions();
        r.eq("isActive", true);
        //endregion

        //region 조회

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort); //PageRequest 설정

        Page<Account> items = accountRepository.findAll(r.output(), pageRequest); //Page DATA GET

        PagableDto.Response<AccountDto.Response> pages = PagableDto.Response.of(items); //Page response mapping

        //endregion

        return pages;
    }

    @Override
    public List<AccountDto.Response> getAccounts(AccountDto.Request request) {
        return null;
    }

    @Override
    public AccountDto.Response getAccount(long id) {
        return null;
    }

    @Override
    public AccountDto.Response createAccount(AccountDto.Create create) {
        return null;
    }

    @Override
    public AccountDto.Response updateAccount(long id, AccountDto.Update update) {
        return null;
    }

    @Override
    public AccountDto.Response deleteAccount(long id) {
        return null;
    }
}
