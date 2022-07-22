package com.template.api.apps.Account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AccountDto {
    @Getter
    @Setter
    @ApiModel("AccountDto_Request")
    public static class Request extends PagableDto.Request{
        @ApiModelProperty(value = "검색어", position = 1)
        private String keyword;
    }

    @Getter
    @Setter
    @ApiModel("AccountDto_Response")
    public static class Response{

        @ApiModelProperty(value = "회원관리번호")
        private Long id;

        @ApiModelProperty(value = "사용자 id")
        private String userId;

        @ApiModelProperty(value = "사용자 명")
        private String name;

    }

    @Getter
    @Setter
    @ApiModel("AccountDto_Create")
    public static class Create {

        @ApiModelProperty(value = "사용자 id")
        private String userId;

        @ApiModelProperty(value = "사용자 명")
        private String name;

        @ApiModelProperty(value = "패스워드")
        private String password;
    }

    @Getter
    @Setter
    @ApiModel("AccountDto_Update")
    public static class Update {


        @ApiModelProperty(value = "사용자 id")
        private String userId;

        @ApiModelProperty(value = "사용자 명")
        private String name;

        @ApiModelProperty(value = "패스워드")
        private String password;

    }


    @Getter
    @Setter
    @ApiModel("AccountDto_Delete")
    public static class Delete{
        private Long id;
    }

    @Data
    @ApiModel("관리자 정보")
    public static class CmsAccountAdminResponse {

        @ApiModelProperty(value = "회원관리번호")
        private Long id;

        @ApiModelProperty(value = "사용자 id")
        private String signName;

        @ApiModelProperty(value = "사용자 명")
        private String name;

      /*  @ApiModelProperty(value = "조직 정보")
        private OrganizationDto.Simple organization;*/

        @ApiModelProperty(value = "연락처")
        private String phone;

        @ApiModelProperty(value = "관리자 활성화 여부")
        private Boolean isAdminActive;

        @ApiModelProperty(value = "최종로그인")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime lastLoginAt;

        @ApiModelProperty(value = "비밀번호 변경일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime passwordChangeAt;

        @ApiModelProperty(value = "잠금여부")
        private Boolean isLock = false;
    }

}
