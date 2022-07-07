package com.template.api.apps.review.controller;

import com.template.api.apps.review.dto.ReviewDto;
import com.template.api.apps.review.service.ReviewService;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"후기 게시판"})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v2/reviews")

public class ReviewController {
    private final ReviewService reviewService;
    @ApiOperation(value = "게시글 목록 확인")
    @GetMapping
    public PagableDto.Response<ReviewDto.Response> getReviews(ReviewDto.Request request) {
        return reviewService.getPageReviews(request);
    }
    @ApiOperation(value = "글작성")
    @PostMapping
    public void createReview(@RequestBody ReviewDto.Create create) throws NullPointerException {
        reviewService.createReview(create);
    }
    @ApiOperation(value = "글 수정")
    @PutMapping("/{id}")
    public void UpdateReview(@PathVariable Long id, ReviewDto.Update update) throws NotFoundException {
        reviewService.update(id, update);
    }
    @ApiOperation(value = "글삭제")
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) throws NotFoundException {
        if(id != null) {
            reviewService.deleteReview(id);
        } else {
            throw new NotFoundException("잘못된 글 번호 .");
        }
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "상세조회")
    public ReviewDto.Response detail(@PathVariable Long id) {
        return reviewService.detail(id);
    }

}
