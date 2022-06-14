package com.template.api.apps.reviews.controller;

import com.template.api.apps.reviews.dto.ReviewDto;
import com.template.api.apps.reviews.service.ReviewService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"01. [USER] 고객 후기"})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/review")

public class ReviewController {
    private final ReviewService reviewService;

    @ApiOperation(value = "게시글 목록 확인")
    @GetMapping
    public PagableDto.Response<ReviewDto.Response> getReviews(ReviewDto.Request request) {
        return reviewService.getPageReviews(request);
    }

    @ApiOperation(value = "글작성")
    @PostMapping
    public void createReview(ReviewDto.Create create) {
        reviewService.createReview(create);
    }

    @ApiOperation(value = "글 수정")
    @PutMapping
    public void UpdateReview(Long id, ReviewDto.Update update) throws NotFoundException {
        reviewService.updateReview(id, update);
    }
    @ApiOperation(value = "글삭제")
    @DeleteMapping
    public void deleteReview(Long id) throws NotFoundException {
        if(id != null) {
            reviewService.deleteReview(id);
        } else {
            throw new NotFoundException("잘못된 글 번호.");
        }
    }
}
