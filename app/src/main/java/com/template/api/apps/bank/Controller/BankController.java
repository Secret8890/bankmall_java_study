package com.template.api.apps.bank.Controller;


import com.amazonaws.services.kms.model.NotFoundException;
import com.template.api.apps.bank.dto.BankDto;
import com.template.api.apps.bank.service.BankServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "01. [BANK] 은행관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v2/bank")
@Secured({"ROLE_BANK"})

public class BankController {

    private final BankServiceImpl bankService;

    @ApiOperation( value = "금융사 명 등록")
    @PostMapping
    public void createBankName(BankDto.Create create) {bankService.createBankName(create);}


    @ApiOperation(value = "금융사명 조회")
    @GetMapping
    public List<BankDto.Response> getBanksName() {return bankService.getBanksName();}

    @ApiOperation(value = "금융사 삭제")
    @DeleteMapping
    public void deleteBank(Long id) throws NotFoundException{
        if(id != null){
            bankService.deleteBank(id);
        } else {
            throw new NotFoundException(" 없는 id 입니다");
        }
    }

}
