package com.template.api.apps.reviews.dto;

import com.template.api.apps.reviews.domain.Review;
import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.reviews.domain.Review;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReviewDtoMapper extends BaseDtoMapper<Review,ReviewDto.Response,ReviewDto.Create,ReviewDto.Update> {
   ReviewDtoMapper INSTANCE = Mappers.getMapper(ReviewDtoMapper.class);

   List<ReviewDto.Response> toResponses(List<Review> reviews);
   Review create(ReviewDto.Create create);

}
