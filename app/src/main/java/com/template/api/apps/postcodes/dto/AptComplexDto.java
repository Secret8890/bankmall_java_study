package com.template.api.apps.postcodes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class AptComplexDto {

    public static List<String> FIELDS = Lists.newArrayList("id",
            "addr1",
            "addr2",
            "addr3",
            "addr4",
            "addr5",
            "addr6",
            "addr7",
            "name",
            "totalLive",
            "totalDong",
            "maxFloor",
            "minFloor",
            "yearMonth",
            "builder",
            "heating",
            "heatingOil",
            "parkingCount",
            "managerPhone",
            "isTicket",
            "inYearMonth",
            "description",
            "postcode",
            "isMultiflex",
            "isVilla",
            "bcode",
            "isRemodeling",
            "roadCode",
            "roadDongCode",
            "roadSideCode",
            "roadAreaCode",
            "isUnder",
            "roadBuildMainNo",
            "roadBuildSubNo",
            "roadBuildMgrNo",
            "roadAddress",
            "roadAddressDetails");


    @Data
    @NoArgsConstructor
    @ApiModel("AptComplexDto_Request")
    public static class Request extends PagableDto.Request {
        private String searchText;


//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    private LocalDate updatedOn; // 업데이트 날짜
    }

    @Data
    public static class Response {

        private String id; //  물건식별자	CHAR(9)

        private String addr1; //  시도명	VARCHAR2(30)

        private String addr2; //  시군구명	VARCHAR2(30)

        private String addr3; //  구명	VARCHAR2(30)

        private String addr4; //  읍면동명	VARCHAR2(30)

        private String addr5; //  리명	VARCHAR2(30)

        private String addr6; //  번지	VARCHAR2(20)

        private String addr7; //  기타번지	VARCHAR2(100)

        private String name; //  단지명	VARCHAR2(40)

        private Long totalLive; //  총세대수	NUMBER(7)

        private Long totalDong; //  총동수	NUMBER(7)

        private Long maxFloor; //  단지최고층	NUMBER(3)

        private Long minFloor; //  단지최저층	NUMBER(3)

        private String yearMonth; //  준공년월	VARCHAR2(6)

        private String builder; //  건설업체명	VARCHAR2(40)

        private String heating; //  난방방식	VARCHAR2(20)

        private String heatingOil; //  난방연료	VARCHAR2(20)

        private Long parkingCount; //  주차대수	NUMBER(5)

        private String managerPhone; //  관리사무소전화번호	VARCHAR2(16)

        private String isTicket; //  분양권여부	VARCHAR2(1)

        private String inYearMonth; //  입주년월	VARCHAR2(6)

        private String description; //  단지특징	VARCHAR2(200)

        private String postcode; //  우편번호	VARCHAR2(6)

        private String isMultiflex; //  주상복합여부	VARCHAR2(1)

        private String isVilla; //  빌라연립여부	VARCHAR2(1)

        private String bcode; //  법정동코드	VARCHAR2(10)

        private String isRemodeling; //  리모델링여부	VARCHAR2(1)

        private String roadCode; //  도로명 우편번호	CHAR(12)

        private String roadDongCode; //  도로명 우편읍면번호	CHAR(2)

        private String roadSideCode; //  도로명 우변보조번호	CHAR(5)

        private String roadAreaCode; //  도로명 구역번호	CHAR(5)

        private String isUnder; //  지하구분	CHAR(1)

        private String roadBuildMainNo; //  도로명 건물본번	CHAR(5)

        private String roadBuildSubNo; //  도로명 건물부번	CHAR(5)

        private String roadBuildMgrNo; //  도로명 건물관리번호	CHAR(25)

        private String roadAddress; //  도로기본주소	CHAR(120)

        private String roadAddressDetails; //  도로상세주소	CHAR(120)
    }


    /*
          데이터 기준 설정 - KB 시세 Response
    */
    @Data
    @ApiModel("AptComplexDto_")
    public static class KbResponse {

        private String id; //  물건식별자	CHAR(9)

        private String name; // 아파트명

        private String roadAddress; // 주소

        private String searchText;  // 지번 주소

        private String searchTextRoad;  // 도로 주소

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedOn; // 업데이트 날짜

    }

}
