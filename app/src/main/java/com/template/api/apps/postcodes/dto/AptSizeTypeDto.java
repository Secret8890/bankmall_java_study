package com.template.api.apps.postcodes.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

public class AptSizeTypeDto {

    public static List<String> FIELDS = Lists.newArrayList(
            "idAptComplexId", "idSizeNo",
            "size", "type", "onlySize",
            "homeCount", "roomCount", "bathCount",
            "salePrice", "direction",
            "entrance", "others"
    );

    @Data
    public static class Response {

        private String idAptComplexId; //  물건식별자	CHAR(9)
        private Integer idSizeNo;

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

    }

}
