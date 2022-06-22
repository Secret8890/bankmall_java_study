package com.template.api.apps.banks.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.service.BankService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Api(tags = {" 금융사 관리 "})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v2/banklists")
@Secured({"ID_USER"})

public class BankController {

    private final BankService bankService;

    @ApiOperation(value = " 금융사 조회 ")
    @GetMapping
    public PagableDto.Response<BankDto.Response> getBanks(BankDto.Request request) {
        return bankService.getPageBanks(request);
    }

    // 금융사 등록

    @ApiOperation(value = " 금융사 등록 ")
    @PostMapping
//    public void createBank(@RequestBody BankDto.Create create) throws NotFoundException{
        public void createBank(BankDto.Create create) throws NotFoundException{
        bankService.createBank(create);

    }
    @ApiOperation(value =" 할인금리 리스트 등록")
    @PostMapping ("/list")
    public void createList (BankDto.SaleCreate salecreate) throws NotFoundException {
        bankService.createList(salecreate);
    }

//    @ApiOperation(value = "할인금리 리스트")
//    @GetMapping("/list/view")
//    public PagableDto.Response<BankDto.SaleResponse> getLists(BankDto.Request request){
//
//    }

}

