package com.template.api.apps.reviews.controller;

import com.template.api.apps.reviews.domain.Review;
import com.template.api.apps.reviews.dto.ReviewDto;
import com.template.api.apps.reviews.service.ReviewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"[USER] 고객 후기"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public void createReview(ReviewDto.Create create) {
        reviewService.createReview(create);
    }
    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getReviews();
    }

}
