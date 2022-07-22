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
public class AptSizeType extends BaseDomain {

    @EmbeddedId
    private PK id;

    private Double size; //  공급면적	NUMBER(9.2)
    private String type; //  평형구분	VARCHAR2(10)
    private Double onlySize; //  전용면적	NUMBER(9,2)
    private Integer homeCount; //  세대수	NUMBER(7)
    private Integer roomCount; //  방수	NUMBER(3)
    private Integer bathCount; //  욕실수	NUMBER(3)
    private Long salePrice; //  분양가	NUMBER(15)
    private String direction; //  방향	VARCHAR2(6)
    private String entrance; //  현관구조	VARCHAR2(20)
    private String others; //  기타전용면적	VARCHAR2(250)

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class PK implements Serializable {

        @ManyToOne
        private AptComplex aptComplex; //  물건식별자	CHAR(9)

        private Integer sizeNo;
    }


}
