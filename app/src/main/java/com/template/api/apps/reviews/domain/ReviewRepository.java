package com.template.api.apps.reviews.domain;

import com.template.api.apps.accounts.domain.Account;
import com.template.api.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends BaseRepository<Review> {

    Optional<Review> findByUserid(String userId);

}
