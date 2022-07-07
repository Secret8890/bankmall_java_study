package com.template.api.apps.reviews.service;

import com.template.api.apps.Account.domain.Account;
import com.template.api.apps.Account.domain.AccountRepository;
import com.template.api.apps.reviews.domain.Review;
import com.template.api.apps.reviews.domain.ReviewRepository;
import com.template.api.apps.reviews.dto.ReviewDto;
import com.template.api.apps.reviews.dto.ReviewDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Primary
@Qualifier("ReviewServiceImpl")

public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    @Transactional
    public void createReview(ReviewDto.Create create) {
        if (create.getContent() == null) {
            throw new NullPointerException("데이터가 존재하지 않습니다");
        } else {
            Review review = ReviewDtoMapper.INSTANCE.create(create);
            Account account = accountRepository.findById(create.getCreatedById()).orElse(null);
            if (account!=null){
                review.setCreatedBy(account);
            }
            reviewRepository.save(review);
        }
    }
}
