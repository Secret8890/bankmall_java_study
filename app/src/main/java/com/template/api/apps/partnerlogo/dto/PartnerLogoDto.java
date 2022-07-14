package com.template.api.apps.partnerlogo.dto;

import com.bankmall.apps.common.dto.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class PartnerLogoDto {

    @Getter
    @Setter
    @ApiModel("partnerlogoDto_Request")
    public static class Request extends PagableDto.Request {
        @ApiModelProperty(value = "검색어", position = 1)
        private String keyword;
    }
    @Getter
    @Setter
    @ApiModel("partnerlogoDto_Response")

    public static class Response {
        @ApiModelProperty(value = "id")
        private Long id;

        @ApiModelProperty(value = "파트너사 명칭")
        private String partnerName;

        @ApiModelProperty(value = "URL 주소")
        private String URL;

        @ApiModelProperty(value = "사용여부")
        private boolean isActive;

        @ApiModelProperty(value = "PC이미지")
        private String pcImage;

        @ApiModelProperty(value = "MO이미지")
        private String moImage;

    }
    @Getter
    @Setter
    @ApiModel("partnerlogoDto_Create")

    public static class Create {
        @ApiModelProperty(value = "URL 주소")
        private String URL;

        @ApiModelProperty(value = "사용여부")
        private boolean isActive;

        @ApiModelProperty(value = "PC이미지")
        private String pcImage;

        @ApiModelProperty(value = "MO이미지")
        private String moImage;
    }

    @Getter
    @Setter
    @ApiModel("partnerLogo_Update")
    public static class Update {

        @ApiModelProperty(value = "id")
        private Long id;

        @ApiModelProperty(value = "URL 주소")
        private String URL;

        @ApiModelProperty(value = "사용여부")
        private boolean isActive;

        @ApiModelProperty(value = "PC이미지")
        private String pcImage;

        @ApiModelProperty(value = "MO이미지")
        private String moImage;

    }

    @Getter
    @Setter
    @ApiModel("partnerlogo_detail")
    public static class Detail {
        @ApiModelProperty("조회할 아이디")
        private Long id;

    }
    @Getter
    @Setter
    @ApiModel("partnerlogo_delete")
    public static class Delete{

        @ApiModelProperty(value = "삭제할 아이디")
        private Long id;
    }
}