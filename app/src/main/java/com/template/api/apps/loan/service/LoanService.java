package com.template.api.apps.loan.service;


import com.template.api.apps.loan.domain.Loan;
import com.template.api.apps.loan.domain.LoanRepository;
import com.template.api.apps.loan.dto.LoanDto;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class LoanService {

    private final LoanRepository loanRepository;

    // 모든 상품 조회
    public List<Loan> index() {
        return loanRepository.findAll();
    }

    //id 값을 입력하면 조회
    public Loan show (@PathVariable Long id) {return loanRepository.findById(id).orElse(null); }

    @Transactional
    public Loan create(LoanDto dto) {
        Loan loan = dto.toEntity();
        if (loan.getId() != null) {
            return null;
        }
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan update(Long id, LoanDto dto) {
        // 1. DTO를 엔티티로 변환
        Loan loan = dto.toEntity();
        log.info(dto.toEntity().toString());
        // 2. 타겟 조회
        Loan target = loanRepository.findById(id).orElse(null);
        target.setAddRate(dto.getAddRate());

        log.info(target.toString());
        // 3. 잘못된 요청 처리
        if (target == null) {
            // 400 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}", id);
            return null;
        }
        // 4. 업데이트
        target.put(loan);
        Loan updated = loanRepository.save(target);
        return updated;
    }

    @Transactional
    public Loan delete(Long id) {
        // 대상 찾기
        Loan target = loanRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }
        // 대상삭제
        loanRepository.delete(target);
        return target;
    }
}