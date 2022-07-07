package com.template.api.apps.reviews.dto;

import com.template.api.apps.reviews.domain.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewDtoMapper {
    ReviewDtoMapper INSTANCE = Mappers.getMapper(ReviewDtoMapper.class);
    Review create(ReviewDto.Create create);
}
