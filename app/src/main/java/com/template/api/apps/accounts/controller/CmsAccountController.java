package com.template.api.apps.accounts.controller;

import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.apps.accounts.service.AccountService;
import com.template.api.apps.accounts.service.AccountServiceImpl;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"00. [CMS] 회원관리 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/cms/account")
@Secured({"ROLE_ADMIN"})
public class CmsAccountController {

    @Qualifier("QAccountServiceImpl")
    private final AccountService accountService;

    @ApiOperation(value = "모든 계정 목록 조회 (QueryDSL)")
    @GetMapping()
    public PagableDto.Response<AccountDto.Response> getAccounts(AccountDto.Request request) {
        return accountService.getPageAccounts(request);
    }
}
