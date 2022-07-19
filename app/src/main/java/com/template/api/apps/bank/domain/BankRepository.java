package com.template.api.apps.bank.domain;

import com.template.api.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends BaseRepository<Bank> {

    Optional<Bank> findByBankId(String bankId);
    Long countByBankId(String username);
    void deleteById(Long id);

    
}
