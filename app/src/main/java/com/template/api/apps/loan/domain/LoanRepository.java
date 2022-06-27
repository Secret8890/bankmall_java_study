package com.template.api.apps.loan.domain;

import com.template.api.jpa.base.BaseRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface LoanRepository extends BaseRepository<Loan> {

    Optional<Loan> findById(String id);
}

//public interface LoanRepository extends CrudRepository<Loan, Long> {
//    @Override
//    ArrayList<Loan> findAll();
//}
