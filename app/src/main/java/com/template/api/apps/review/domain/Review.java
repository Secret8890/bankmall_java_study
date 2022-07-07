package com.template.api.apps.review.domain;

import com.template.api.apps.review.dto.ReviewDto;
import com.template.api.apps.review.dto.ReviewDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Review extends BaseDomainWithId implements DomainWithMapper<ReviewDto.Response> {

    private String title;

    private String content;

    @Override
    public ReviewDto.Response toResponse() {
        return ReviewDtoMapper.INSTANCE.toResponse(this);
    }
}


