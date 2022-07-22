package com.template.api.apps.postcodes.dto;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

public class PostcodeDto {

    public static List<String> LEGACY_FIELDS = Lists.newArrayList(
            "id", "seq",
            "dongCode", "sido", "gugun",
            "dong", "ri", "isMount",
            "bunji", "ho",
            "isMaster"
    );

    public static List<String> ROAD_FIELDS = Lists.newArrayList(
            "id", "roadCode", "dongCode", "isUnder", "buildMainCode", "buildSubCode", "structNo",
            "editType", "registAt", "oldCode", "detailAddress"
    );


    public static List<String> EXTENDS_FIELDS = Lists.newArrayList(
            "id", "dongCode", "dongName", "postcode", "postcodeDetails", "centerName", "buildName1",
            "buildName2", "withus"
    );

    @Data
    public static class Request {

        private String type;
        private String keyword;
        private String mid;
        private String kbid2;

    }

    @Data
    public static class Response {

        private String label;
        private String name;
        private String address;
        private String bcode;
        private String kbId;
        private String kbId2;
        private String specType;


        private Long totalLive;
        private String addrId;
        private String lowestLabel;
        private String buildType;
    }

    @AllArgsConstructor
    @Getter
    public static class UploadDto {
        private int index; //
        private String uploadZipName; //업로드 알집명
        private String uploadFileName; //업로드 파일명
        private List<String> rows;
    }

}
