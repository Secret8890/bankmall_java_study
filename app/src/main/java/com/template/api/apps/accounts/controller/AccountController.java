package com.template.api.apps.accounts.controller;

import ch.qos.logback.core.CoreConstants;
import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.apps.accounts.service.AccountService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PutMapping("/user/update")
    public void UpdateAccount(AccountDto.Update update){
       accountService.updateAccount(update);

    }

    @ApiOperation(value = "세번째야")
    @PostMapping("/user/id")
    @ResponseBody
    public void getName(@PathVariable(name = "name") String name) {
    }

    @ApiOperation(value = "네번째야")
    @RequestMapping(value = "user/email", method = RequestMethod.GET)
    public void getUserByEmail(@PathVariable("email") String email) {

    }

//    @RestController
//    @RequestMapping("/api/v1/user")
//    public class UserController {
//
//        @Autowired
//        private final UserService userService;
//
//        @GetMapping("")
//        public ResponseEntity<List<UserResponse>> getAllUser() {
//            List<UserResponse> userList = userService.getAllUser();
//            return ResponseEntity.status(HttpStatus.OK).body(userList);
//        }

}
