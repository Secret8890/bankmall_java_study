package com.template.api.apps.loan.service;


import com.template.api.apps.loan.domain.Loan;
import com.template.api.apps.loan.domain.LoanRepository;
import com.template.api.apps.loan.dto.LoanDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public List<Loan> selectLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoan(Long id) {
        return loanRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    public Loan updateLoan(Long id, Loan loan) {
        Loan loanData = loanRepository.findById(id).orElse(null);
        loanData.update(loan);
        return loanRepository.save(loan);
    }
}