package com.template.api.apps.review.domain;

import com.template.api.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends BaseRepository<Review> {

    Optional<Review> findById(String userId);

}
