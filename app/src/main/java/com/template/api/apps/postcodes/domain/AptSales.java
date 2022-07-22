package com.template.api.apps.postcodes.domain;


import com.template.api.jpa.base.BaseDomainWithId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
public class AptSales extends BaseDomainWithId {

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Method method;

    @ManyToOne
    private AptComplex aptComplex;

    private String sigungu;

    private String bunji;

    private String mainNo;

    private String subNo;

    private String name;

    private Double onlySize;

    private LocalDate saledAt;

    private Long price;

    private Long deposit;

    private Integer floor;

    private Integer buildYear;

    private String road;

    private String failure;

    private String saleType;

    private String manager;

    public enum Type {
        APT,
        OFT
    }

    public enum Method {
        DEAL,
        LEASE,
        LEASE_MONTH
    }

}
