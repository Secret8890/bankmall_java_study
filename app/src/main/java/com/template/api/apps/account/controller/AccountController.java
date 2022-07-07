package com.template.api.apps.account.controller;

import com.template.api.apps.account.dto.AccountDto;
import com.template.api.apps.account.service.AccountService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"회원관리"})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v2/account")
//@Secured({"ROLE_USER"})
public class AccountController {

    private final AccountService accountService;

    @ApiOperation(value = "계정 조회")
    @GetMapping
    public PagableDto.Response<AccountDto.Response> getAccounts(AccountDto.Request request) {
        return accountService.getPageAccounts(request);
    }

    @ApiOperation(value = "회원가입")
    @PostMapping
    public void createAccount(@RequestBody AccountDto.Create create) {

        accountService.createAccount(create);
    }

    @ApiOperation(value = "회원변경")
    @PutMapping("/{id}")
    public void UpdateAccount(@PathVariable Long id, @RequestBody AccountDto.Update update)  {
        accountService.update(id, update);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "회원 상세조회")
    public AccountDto.Response detail(@PathVariable Long id) {
        return accountService.detail(id);

    }

    @ApiModelProperty(value = "회원삭제")
    @DeleteMapping("/{id}")
    public void DeleteAccount(@PathVariable Long id) throws NullPointerException{
        accountService.deleteaccount(id);
    }
}
