package com.template.api.apps.loan.controller;

import com.template.api.apps.loan.domain.Loan;
import com.template.api.apps.loan.dto.LoanDto;
import com.template.api.apps.loan.service.LoanService;
import com.template.api.apps.member.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"대출상품 관리"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/loan")
//@Secured({"ROLE_USER"})
public class LoanController {

    private final LoanService loanService;

    //대출상품 불러오기
    @ApiOperation(value = "대출상품 불러오기")
    @GetMapping
    public List<Loan> selectLoans() {
        return loanService.selectLoans();
    }
    // 특정 대출상품 불러오기
    @ApiOperation(value = "특정 대출상품 불러오기")
    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable Long id) {
        return loanService.getLoan(id);
    }
    // 대출상품 추가하기
    @ApiOperation(value = "대출상품 추가하기")
    @PostMapping
    public Loan saveLoan(@RequestBody Loan loan) {
        return loanService.saveLoan(loan);
    }

    // 대출 수정하기
    @ApiOperation(value = "대출상품 수정하기")
    @PutMapping("/{id}")
    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        return loanService.updateLoan(id, loan);
    }

    // 대출 삭제하기
    @ApiOperation(value = "대출상품 삭제하기")
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
