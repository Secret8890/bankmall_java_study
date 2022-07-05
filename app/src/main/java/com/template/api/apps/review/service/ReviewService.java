package com.template.api.apps.review.service;


import com.template.api.apps.review.domain.Review;
import com.template.api.apps.review.domain.ReviewRepository;
import com.template.api.apps.review.dto.ReviewDto;
import com.template.api.apps.review.dto.ReviewDtoMapper;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional

    public void createReview(ReviewDto.Create create) throws NullPointerException{
        if (create == null) {
            throw new NullPointerException("데이터가 존재하지 않습니다..");
        } else {

            Review review = ReviewDtoMapper.INSTANCE.create(create);

            reviewRepository.save(review);
        }
    }
    @Transactional

    public PagableDto.Response<ReviewDto.Response> getPageReviews(ReviewDto.Request request) {


        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        if(StringUtils.isNotBlank(request.getSort())){
            sort = Sort.by(request.getOrder().dir, request.getSort());
        }

        Restrictions r = new Restrictions();
        r.eq("isActive", true);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);

        Page<Review> items = reviewRepository.findAll(r.output(), pageRequest);

        PagableDto.Response<ReviewDto.Response> pages = PagableDto.Response.of(items);

        return pages;
    }

//    public List<ReviewDto.Response> getReviews(ReviewDto.Request request) {
//        return null;
//    }

    @Transactional
    public void update(Long id, ReviewDto.Update update) throws NullPointerException {

        Review review = reviewRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 입니다."));

        ReviewDtoMapper.INSTANCE.update(update,review);


    }

    @Transactional
    public void deleteReview(long id) {
        reviewRepository.deleteById(id);
    }

    public ReviewDto.Response detail (Long id){
        Review review = reviewRepository.findById(id).orElseThrow(()->new NullPointerException("잘못된 ID 값입니다."));
        ReviewDto.Response response = review.toResponse();

        return response;

    }



}