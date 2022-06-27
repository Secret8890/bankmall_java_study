package com.template.api.apps.loan.domain;

import com.template.api.jpa.base.BaseDomainWithId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
        import javax.persistence.FetchType;
        import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Sales extends BaseDomainWithId {

    @ManyToOne(fetch = FetchType.LAZY)
    private Loan loan;

    private String rateBySpecial; // 할인항목

    private Double disCountRows; // 할인금리
}