package com.template.api.apps.Account.controller;

import com.template.api.apps.Account.dto.AccountDto;
import com.template.api.apps.Account.service.AccountService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Api(tags = "01. [USER] 고객관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/account")
@Secured({"ROLE_USER"})
public class AccountController {

    private  final AccountService accountService;

    @ApiOperation(value = " 모든 회원 조회")
    @GetMapping
    public PagableDto.Response<AccountDto.Response>getAccounts(AccountDto.Request request){
        return accountService.getPageAccounts(request);
    }
    @ApiOperation(value = "회원가입")
    @PostMapping
    public void createAccount(AccountDto.Create create){
        accountService.createAccount(create);
    }
    @ApiOperation(value = "회원정보 수정")
    @PatchMapping
    public void UpdateAccount(Long id, AccountDto.Update update) {
        accountService.updateAccount(id, update);
    }


}
