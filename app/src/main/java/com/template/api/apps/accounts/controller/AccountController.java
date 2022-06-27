package com.template.api.apps.accounts.controller;

import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.apps.accounts.service.AccountService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"00. [USER] 회원관리 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/account")
@Secured({"ROLE_USER"})
public class AccountController {

    private final AccountService accountService;

    @ApiOperation(value = "모든 계정 목록 조회 (JPA)")
    @GetMapping
    public PagableDto.Response<AccountDto.Response> getAccounts(AccountDto.Request request) {
        return accountService.getPageAccounts(request);
    }



    // todo : 회원가입 구현
    @ApiOperation(value = "회원가입")
    @PostMapping
    public void createAccount(AccountDto.Create create) {
        //return accountService.getPageAccounts(request);
        accountService.createAccount(create);
    }

    // todo : 회원변경 구현
    @ApiOperation(value = "회원정보 변경")
    @PutMapping
    public void UpdateAccount(Long id, AccountDto.Update update) throws NotFoundException {
        accountService.updateAccount(id, update);
    }

}
