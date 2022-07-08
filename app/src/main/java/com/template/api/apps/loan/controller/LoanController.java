package com.template.api.apps.loan.controller;

import com.template.api.apps.loan.domain.Loan;
import com.template.api.apps.loan.dto.LoanDto;
import com.template.api.apps.loan.service.LoanService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"대출상품 관리"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/loan")
//@Secured({"ROLE_USER"})
public class LoanController {

    private final LoanService loanService;

    @ApiOperation(value = "대출상품 조회")
    @GetMapping
    public List<Loan> index() {
        return loanService.index();
    }

//    @ApiOperation(value = "금융사명으로 조회")
//    @GetMapping()
//    public Loan search(@RequestParam(value = "keyword") String keyword) {
//        List<LoanDto> loanDtoList = loanService
//    }

    @ApiOperation(value = "대출상품 등록")
    @PostMapping
    public ResponseEntity<Loan> create(LoanDto dto) {
        Loan created = loanService.create(dto);
        return created != null ? ResponseEntity.status(HttpStatus.OK).body(created) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "대출상품 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Loan> update(@PathVariable Long id, LoanDto dto) {
        Loan updated = loanService.update(id, dto);
        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated):
        ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "대출상품 삭제")
    @DeleteMapping
    public ResponseEntity<Loan> delete(Long id) {
        Loan deleted = loanService.delete(id);
        return (deleted != null) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
