package com.template.api.apps.reviews.service;


import com.template.api.apps.reviews.domain.Review;
import com.template.api.apps.reviews.domain.ReviewRepository;
import com.template.api.apps.reviews.dto.ReviewDto;
import com.template.api.apps.reviews.dto.ReviewDtoMapper;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;

    //    public PagableDto.Response<ReviewDto.Response> getPageReviews(ReviewDto.Request request)
    @Transactional

    public void createReview(ReviewDto.Create create) {
        if (create.getContent() == null) {
            throw new NullPointerException("데이터가 존재하지 않습니다..");
        } else {
            Review review = ReviewDtoMapper.INSTANCE.create(create);
//            review.setTitle(create.getTitle());
//            review.setContent(create.getContent());
//            review.setUserid(create.getUserid());
            reviewRepository.save(review);
        }
    }


    @Transactional

    public PagableDto.Response<ReviewDto.Response> getPageReviews(ReviewDto.Request request) {


        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if (StringUtils.isNotBlank(request.getSort())) {
            sort = Sort.by(request.getOrder().dir, request.getSort());
        }

        Restrictions r = new Restrictions();
        r.eq("isActive", true);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);

        Page<Review> items = reviewRepository.findAll(r.output(), pageRequest); //Page DATA GET

        PagableDto.Response<ReviewDto.Response> pages = PagableDto.Response.of(items); //Page response mapping

        //endregion

        return pages;
    }


    public List<ReviewDto.Response> getReviews(ReviewDto.Request request) {
        return null;
    }


    @Transactional
    public void updateReview(Long id, ReviewDto.Update update) throws NotFoundException {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            review.setUserid(update.getId());
            review.setTitle(update.getTitle());
            review.setContent(update.getContent());
        } else {
            throw new NotFoundException("해당 번호 게시글이 없습니다 . ");
        }

    }

    @Transactional
    public void deleteReview(long id) {
        reviewRepository.deleteById(id);
    }
}