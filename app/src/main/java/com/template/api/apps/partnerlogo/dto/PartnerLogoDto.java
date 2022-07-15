package com.template.api.apps.partnerlogo.dto;


import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


public class PartnerLogoDto {

    @Getter @Setter
    @ApiModel("PartnerLogoDto_Request")
    public  static class Request extends PagableDto.Request{
        @ApiModelProperty(value = "검색어")
        private String keyword;
    }

    @Getter @Setter
    @ApiModel("PartnerLogoDto_Response")
    public static class Response{
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


    @Getter @Setter
    @ApiModel("PartnerLogoDto_Create")
    public static class Create{
        @ApiModelProperty(value = "URL 주소")
        private String URL;

        @ApiModelProperty(value = "사용여부")
        private boolean isActive;

        @ApiModelProperty(value = "PC이미지")
        private String pcImage;

        @ApiModelProperty(value = "MO이미지")
        private String moImage;

    }

    @Getter @Setter
    @ApiModel("partnerLogoDto_Update")
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

    @Getter @Setter
    @ApiModel("PartnerLogo_Delete")
    public static class Delete{

        @ApiModelProperty(value = "삭제할 ID")
        private Long id;
    }
}
