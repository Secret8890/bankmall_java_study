package com.template.api.apps.banks.domain;

import com.template.api.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BankRepository extends BaseRepository<Bank> {

    Optional<Bank> findByid(String id);

//    Long countByid(Long id);
//    @Query(value = "select * FROM banks WHERE Bank_name LIKE %:keyword%"
//    )
//    List<Bank> findAll(String keyword);


}
