package com.template.api.apps.account.service;

import com.template.api.apps.account.domain.Account;
import com.template.api.apps.account.domain.AccountRepository;
import com.template.api.apps.account.dto.AccountDto;
import com.template.api.apps.account.dto.AccountDtoMapper;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)

public class AccountService {

    private final AccountRepository accountRepository;


    public PagableDto.Response<AccountDto.Response> getPageAccounts(AccountDto.Request request) {
        //region 정렬
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if (StringUtils.isNotBlank(request.getSort())) {
            sort = Sort.by(request.getOrder().dir, request.getSort());
        }

        Restrictions r = new Restrictions();
        r.eq("isActive", true);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort); //PageRequest 설정

        Page<Account> items = accountRepository.findAll(r.output(), pageRequest); //Page DATA GET

        PagableDto.Response<AccountDto.Response> pages = PagableDto.Response.of(items); //Page response mapping


        return pages;
    }

    @Transactional 
    public void createAccount(AccountDto.Create create) {

        if(create == null){
            throw new NullPointerException("입력된 값이 없습니다 ");
        }else {

            Account account = AccountDtoMapper.INSTANCE.create(create);

            accountRepository.save(account);
        }
    }

    public void update(Long id, AccountDto.Update update) throws NullPointerException {

        Account account = accountRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 입니다."));

        AccountDtoMapper.INSTANCE.update(update,account);
    }

    public AccountDto.Response detail (Long id){
        Account account = accountRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 값입니다."));
        AccountDto.Response response = account.toResponse();

        return response;
    }

    @Transactional
    public void deleteaccount(Long id) {
        accountRepository.deleteById(id);
    }
}
