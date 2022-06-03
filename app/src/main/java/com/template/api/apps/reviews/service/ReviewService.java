package com.template.api.apps.reviews.service;

import com.template.api.apps.reviews.domain.Review;
import com.template.api.apps.reviews.domain.ReviewRepository;
import com.template.api.apps.reviews.dto.ReviewDto;
import com.template.api.apps.reviews.dto.ReviewDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public void createReview(ReviewDto.Create create) {
        if(create.getContent() == null) {
            throw new NullPointerException("데이터가 존재하지 않습니다..");
        } else {
            Review review = ReviewDtoMapper.INSTANCE.create(create);
            reviewRepository.save(review);
        }
    }
}
