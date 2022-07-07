package com.template.api.apps.reviews.controller;

import com.template.api.apps.reviews.domain.Review;
import com.template.api.apps.reviews.dto.ReviewDto;
import com.template.api.apps.reviews.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"03. [USER] 고객 후기"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/review")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "모든 후기 보기")
    @GetMapping
    public List<Review> getReviews(){return reviewService.getReviews();}

    @ApiOperation(value = "후기 작성")
    @PostMapping
    public void createReview(ReviewDto.Create create){reviewService.createReview(create);}

}
