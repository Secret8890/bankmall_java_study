package com.template.api.apps.postcodes.domain;


import com.template.api.jpa.base.BaseDomain;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
public class AptPrice extends BaseDomain {

    @EmbeddedId
    private PK id;

    private String addr1; //  시도명	VARCHAR2(30)
    private String addr2; //  시군구명	VARCHAR2(30)
    private String addr3; //  구명	VARCHAR2(30)
    private String addr4; //  읍면동명	VARCHAR2(30)
    private String addr5; //  리명	VARCHAR2(30)
    private String addr6; //  번지	VARCHAR2(20)
    private String addr7; //  기타번지	VARCHAR2(100)
    private String name; //  단지명	VARCHAR2(40)

    private Long subAvrDealPrc; // 하위평균매매가
    private Long nomAvrDealPrc; // 일반평균매매가
    private Long upperAvrDealPrc; // 상위평균매매가

    private Long subAvrJeonsePrc; // 하위평균전세가
    private Long nomAvrJeonsePrc; // 일반평균전세가
    private Long upperAvrJeonsePrc; // 상위평균전세가

    private Long subAvrMonthPrc; // 하위평균월세가
    private Long nomAvrMonthPrc; // 일반평균월세가
    private Long upperAvrMonthPrc; // 상위평균월세가

    private String dcode; //  법정동코드	VARCHAR2(10)


    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class PK implements Serializable {

        private String date;

        @ManyToOne
        private AptComplex aptComplex; //  물건식별자	CHAR(9)

        private Integer sizeNo;
    }


}
