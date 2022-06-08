package com.template.api.apps.accounts.controller;

import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.accounts.domain.AccountRepository;
import com.template.api.apps.accounts.service.AccountService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"00. [CMS] 회원관리 API","01. [회원정보 변경]"})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/cms/account")
@Secured({"ROLE_ADMIN"})
public class CmsAccountController extends Account{
    // todo : 회원 정보 변경 구현


    // todo : 회원 탈퇴 구현
}
