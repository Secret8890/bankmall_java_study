package com.template.api.apps.banks.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.service.BankService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {" 금융사 관리 "})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v2/banklists")
//@Secured({"ID_USER"})
public class BankController {

    private final BankService bankService;

    @ApiOperation(value = " 금융사 조회 ")
    @GetMapping
    public PagableDto.Response<BankDto.Response> getBanks(BankDto.Request request) {
        return bankService.getPageBanks(request);
    }

    @ApiOperation(value = " 금융사 등록 ")
    @PostMapping
    public void createBank(@RequestBody BankDto.Create create) throws NotFoundException {
        bankService.createBank(create);
    }

    @ApiOperation(value = "할인금리 리스트")
    @GetMapping("/list/view")
    public PagableDto.Response<BankDto.SaleResponse> getLists(BankDto.Request response) {
        return bankService.getPageLists(response);
    }

    @ApiOperation(value = "금융사 내용 수정")
    @PutMapping("/{id}")
    public void Update(@PathVariable Long id, @RequestBody BankDto.Update update) {
        bankService.update(id, update);
    }

    @ApiOperation(value = "정보 삭제")
    @DeleteMapping("/{id}")
    public void deleteBank(@PathVariable Long id) throws NotFoundException {
        if (id == null) {
            throw new NotFoundException("올바른 아이디를 입력하세요");
        } else {
            bankService.deleteBank(id);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "상세조회")
    public BankDto.Response detail(@PathVariable Long id) {
        return bankService.detail(id);
    }

//    @ApiOperation(value = " 금리 ")
//    @PostMapping("/rate")
//    public void createRate(@RequestBody BankDto.Create create) throws NotFoundException {
//        bankService.createBank(create);
//    }

//    @GetMapping("/rate")
//    @ApiModelProperty(value = "금리 계산")
//    public Bank CalResponce(BankDto.Request request) throws NotFoundException {
//        return bankService.calResponce(request);
//    }
}
    // request

//}

