package com.template.api.apps.postcodes.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

public class AptPriceDto {

    public static List<String> FIELDS = Lists.newArrayList(
            "idDate", "idAptComplexId", "idSizeNo",
            "addr1", "addr2", "addr3", "addr4", "addr5", "addr6", "addr7",
            "name",
            "subAvrDealPrc", "nomAvrDealPrc", "upperAvrDealPrc",
            "subAvrJeonsePrc", "nomAvrJeonsePrc", "upperAvrJeonsePrc",
            "subAvrMonthPrc", "nomAvrMonthPrc", "upperAvrMonthPrc",
            "dcode"
    );

    @Data
    public static class Response {

        private String idDate;
        private String idAptComplexId; //  물건식별자	CHAR(9)
        private Integer idSizeNo;

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

    }


    @Data
    public static class Simple {

        private Integer sizeNo;

        private String label;

        private Double size; //  공급면적	NUMBER(9.2)
        private String type; //  평형구분	VARCHAR2(10)
        private Double onlySize; //  전용면적	NUMBER(9,2)
        private Integer roomCount; //  방수	NUMBER(3)
        private Integer houseCnt;

        private Long subAvrDealPrc; // 하위평균매매가
        private Long nomAvrDealPrc; // 일반평균매매가
        private Long upperAvrDealPrc; // 상위평균매매가
        //private String totGen;

    }

    @Data
    public static class Official {

        private String dong;

        private String dongCode;

        private String ho;

        private String hoCode;

        private Long officialPrice; // 상위평균매매가

        private Double areaSize;    //공시지가 면적정보

    }


    @Data
    public static class OfficialItemDong {

        private String label;

        private String code;

        private List<OfficialItemHo> items;

    }

    @Data
    public static class OfficialItemHo {

        private String label;

        private String code;

        private Long officialPrice; // 상위평균매매가

        private Double areaSize;    //공시지가 면적정보

    }

}
