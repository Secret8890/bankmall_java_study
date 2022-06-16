package com.template.api.apps.reviews.domain;

import com.template.api.apps.accounts.domain.Account;
import com.template.api.jpa.base.BaseDomainWithId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Review extends BaseDomainWithId {

    private String title;

    private String content;

    @ManyToOne
    private Account createdBy;
}
