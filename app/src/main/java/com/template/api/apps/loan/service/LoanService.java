package com.template.api.apps.loan.service;


import com.template.api.apps.loan.domain.Loan;
import com.template.api.apps.loan.domain.LoanRepository;
import com.template.api.apps.loan.dto.LoanDto;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)

public class LoanService {

    @Autowired
    private final LoanRepository loanRepository;

    // 모든 상품 조회
    public List<Loan> index() {
        return loanRepository.findAll();
    }

    //id 값을 입력하면 조회
    public Loan show (Long id) {return loanRepository.findById(id).orElse(null); }

    public Loan create(LoanDto dto) {
        Loan loan = dto.toEntity();
        if (loan.getId() != null) {
            return null;
        }
        return loanRepository.save(loan);
    }

    public Loan update(Long id, LoanDto dto) {
        Loan loan = dto.toEntity();
        // loan = dto에 담겨진 데이터를 엔티티로 변환
        Loan target = loanRepository.findById(id).orElse(null);
        // target = loanRepository에서 id로 검색하고 없다면 null을 반환
        if (target != null || id != loan.getId()) {
            // 만약에 target이 null값이 아니거나 loan의 id값이 입력된 id값과 다르다면
            return null;
            // null을 반환해라
        }
        target.put(loan);
        Loan updated = loanRepository.save(target);
        return updated;
    }

}