package com.template.api.apps.reviews.domain;

import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.apps.accounts.dto.AccountDtoMapper;
import com.template.api.apps.reviews.dto.ReviewDto;
import com.template.api.apps.reviews.dto.ReviewDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Review extends BaseDomainWithId implements DomainWithMapper<ReviewDto.Response> {

    @Column(nullable = false, unique = true)

    private String userid;

    private String title;

    private String content;
    @Override
    public ReviewDto.Response toResponse() {
        return ReviewDtoMapper.INSTANCE.toResponse(this);
    }
}


