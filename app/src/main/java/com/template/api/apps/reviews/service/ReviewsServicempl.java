package com.template.api.apps.reviews.service;


import com.template.api.apps.reviews.dto.ReviewDto;
import com.template.api.utils.dtos.PagableDto;

import java.util.List;

public interface ReviewsServicempl {

    PagableDto.Response<ReviewDto.Response> getPageReviews(ReviewDto.Response response);

    List<ReviewDto.Response> getReviews(ReviewDto.Request request);
    ReviewDto.Response getReviews(long id , String title , String content);
    void createReview(ReviewDto.Create create);

}
