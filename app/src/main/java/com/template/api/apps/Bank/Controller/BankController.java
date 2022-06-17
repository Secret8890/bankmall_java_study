package com.template.api.apps.Bank.Controller;


import com.template.api.apps.Bank.dto.BankDto;
import com.template.api.apps.Bank.service.BankServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "01. [BANK] 은행관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/bank")
@Secured({"ROLE_BANK"})

public class BankController {

    private final BankServiceImpl bankService;

    @ApiOperation( value = "금융사 명 등록")
    @PostMapping
    public void createBankName(BankDto.Create create) {bankService.createBankName(create);}


    @ApiOperation(value = "금융사명 조회")
    @GetMapping
    public List<BankDto.Response> getBankNames() {return bankService.getBankNames();}

}
