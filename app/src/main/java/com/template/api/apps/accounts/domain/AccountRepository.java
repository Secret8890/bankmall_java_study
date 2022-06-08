package com.template.api.apps.accounts.domain;

import com.template.api.jpa.base.BaseRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account> {

  Optional<Account> findByUserId(String userId);

  Long countByUserId(String username);

  void deleteById(Long id);
}
