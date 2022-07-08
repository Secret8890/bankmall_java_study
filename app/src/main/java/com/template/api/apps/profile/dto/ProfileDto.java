package com.template.api.apps.profile.dto;

import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class ProfileDto {
    @Getter
    @Setter
    @ApiModel("ProfileDto_Request")
    public static class Request extends PagableDto.Request {

        private String keyword;
    }

    @Getter
    @Setter
    @ApiModel("Profile_Response")
    public static class Response{

        @ApiModelProperty(value = "유저아이디")
        private String userId;

        @ApiModelProperty(value = "서브네임")
        private String subname;

        @ApiModelProperty(value = "닉네임")
        private String nickname;

    }

    @Getter
    @Setter
    @ApiModel("Profile_create")
    public static class Create{

        @ApiModelProperty(value = "서브네임")
        private String subname;

    }
    @Getter
    @Setter
    @ApiModel("Profile_update")
    public static class Update{

        @ApiModelProperty(value = "서브네임")
        private String subname;

        @ApiModelProperty(value = "닉네임")
        private String nickname;

    }



    }