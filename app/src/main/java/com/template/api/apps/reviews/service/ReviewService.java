package com.template.api.apps.reviews.service;

import com.template.api.apps.reviews.domain.Review;
import com.template.api.apps.reviews.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    List<Review> getReviews();
    void createReview(ReviewDto.Create create);
}
