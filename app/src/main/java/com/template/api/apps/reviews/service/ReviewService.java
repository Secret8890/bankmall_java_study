package com.template.api.apps.reviews.service;

import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.accounts.domain.AccountRepository;
import com.template.api.apps.reviews.domain.Review;
import com.template.api.apps.reviews.domain.ReviewRepository;
import com.template.api.apps.reviews.dto.ReviewDto;
import com.template.api.apps.reviews.dto.ReviewDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final AccountRepository accountRepository;

    @Transactional
    public void createReview(ReviewDto.Create create) {
        if(create.getContent() == null) {
            throw new NullPointerException("데이터가 존재하지 않습니다..");
        } else {
            Review review = ReviewDtoMapper.INSTANCE.create(create);
            Account account = accountRepository.findById(create.getCreatedById()).orElse(null);
            if(account != null) {
                review.setCreatedBy(account);
            }

            reviewRepository.save(review);
        }
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
}
