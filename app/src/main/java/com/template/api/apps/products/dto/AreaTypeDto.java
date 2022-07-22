package com.template.api.apps.products.dto;

import com.template.api.apps.products.domain.AreaType;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AreaTypeDto {

    @Data
    @NoArgsConstructor
    @ApiModel("AreaTypeDto_Request")
    public static class Request extends PagableDto.Request {

        public enum KeywordType {
            all,
            fullname,
            bcode,
        }

        private KeywordType keywordType = KeywordType.all;

        private String keyword;

        @ApiModelProperty("지역1값")
        private String sido;

        @ApiModelProperty("지역2값")
        private String gugun;

        @ApiModelProperty("지역3값")
        private String dodong;

        @ApiModelProperty("과열지역코드")
        private AreaType.AREA_TYPE areaType;
    }

    @Data
    @NoArgsConstructor
    @ApiModel("AreaTypeDto_Update")
    public static class Update {

        private AreaType.AREA_TYPE type = AreaType.AREA_TYPE.ARTY004;
    }

    @Data
    @NoArgsConstructor
    @ApiModel("AreaTypeDto_Updates")
    public static class Updates {

        private AreaType.AREA_TYPE type = AreaType.AREA_TYPE.ARTY004;
        private List<String> fullname;

        // todo: bcode 에서 fullname으로 변경
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("AreaTypeDto_Response")
    public static class Response {

        private String bcode;

        private String sido;

        private String gugun;

        private String dodong;

        private String fullname;

        private AreaType.AREA_TYPE type = AreaType.AREA_TYPE.ARTY004;

    }

}
