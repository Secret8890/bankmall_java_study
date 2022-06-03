package com.template.api.apps.reviews.domain;

import com.template.api.jpa.base.BaseDomainWithId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Review extends BaseDomainWithId {
    private String title;
    private String content;
}
