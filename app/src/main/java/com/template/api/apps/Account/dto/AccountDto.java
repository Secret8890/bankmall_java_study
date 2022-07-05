package com.template.api.apps.Account.dto;

import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class AccountDto {

    public static class Request extends PagableDto.Request{
        private String keyword;
    }

    @ApiModel("AccountDto_Response")
    public static class Response{

        @ApiModelProperty(value = "회원관리번호")
        private Long id;

        @ApiModelProperty(value = "사용자 id")
        private String userId;

        @ApiModelProperty(value = "사용자 명")
        private String name;

    }

    @ApiModel("AccountDto_Create")
    public static class Create {

        @ApiModelProperty(value = "사용자 id")
        private String userId;

        @ApiModelProperty(value = "사용자 명")
        private String name;

        @ApiModelProperty(value = "패스워드")
        private String password;
    }

    @ApiModel("AccountDto_Update")
    public static class Update {
        @ApiModelProperty(value = "사용자 id")
        private String userId;

        @ApiModelProperty(value = "사용자 명")
        private String name;

        @ApiModelProperty(value = "패스워드")
        private String password;

    }



}
