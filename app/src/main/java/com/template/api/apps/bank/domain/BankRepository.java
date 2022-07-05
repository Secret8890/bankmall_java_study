package com.template.api.apps.bank.domain;

import com.template.api.jpa.base.BaseRepository;

import java.util.Optional;

public interface BankRepository extends BaseRepository<Bank> {

    Optional<Bank> findByBankId(String bankId);
    Long countByBankId(String username);
    void deleteById(Long id);

    
}
