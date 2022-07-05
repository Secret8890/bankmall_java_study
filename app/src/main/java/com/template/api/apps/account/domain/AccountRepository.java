package com.template.api.apps.account.domain;

import com.template.api.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account> {

  Optional<Account> findByUserId(String userId);

  Long countByUserId(String username);

}
