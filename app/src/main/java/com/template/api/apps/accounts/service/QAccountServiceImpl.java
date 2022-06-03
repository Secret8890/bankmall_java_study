package com.template.api.apps.accounts.service;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.accounts.domain.AccountRepository;
import com.template.api.apps.accounts.domain.QAccount;
import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * QueryDSL
 * 복잡한 Query시 사용
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Qualifier("QAccountServiceImpl")
public class QAccountServiceImpl implements AccountService {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 사용자 목록 조회 (Paging)
     */
    @Override
    public PagableDto.Response<AccountDto.Response> getPageAccounts(AccountDto.Request request) {

        QAccount account = QAccount.account; //QueryDSL 사용할 객체 선언

        //QueryResults => Paging 처리를 위한 List
        QueryResults<Account> results =
                jpaQueryFactory.selectFrom(account)
                        .where(account.isActive.eq(true))
                        .orderBy(account.createdAt.desc())
                        .offset(request.getOffset())
                        .limit(request.getLimit())
                        .fetchResults();

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit()); //PageRequest 설정

        Page<Account> items = PagableDto.getPage(results.getResults(), pageRequest, results.getTotal()); //Page Setting

        PagableDto.Response<AccountDto.Response> pages = PagableDto.Response.of(items); //Page response mapping

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
