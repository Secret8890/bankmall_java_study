package com.template.api.apps.account.dto;

import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class AccountDto {

    @Getter
    @Setter
    @ApiModel("AccountDto_Request")
    public static class Request extends PagableDto.Request {
        @ApiModelProperty(value = "검색어", position = 1)
        private String keyword;
    }

    @Getter
    @Setter
    @ApiModel("AccountDto_Response")
    public static class Response {

        @ApiModelProperty(value = "id값")
        private Long id;

        @ApiModelProperty(value = "닉네임")
        private String nickname;

        @ApiModelProperty(value = "사용자 명")
        private String name;
    }

    @Getter
    @Setter
    @ApiModel("AccountDto_Create")
    public static class Create {

        @ApiModelProperty(value = "사용자 id")
        private String userid;

        @ApiModelProperty(value = "사용자 명")
        private String name;

        @ApiModelProperty(value = "패스워드")
        private String password;

        @ApiModelProperty(value = "닉네임")
        private String nickname;
    }

    @Getter
    @Setter
    @ApiModel("AccountDto_Update")
    public static class Update {

        @ApiModelProperty(value = "id")
        private String userId;

        @ApiModelProperty(value = "사용자 명")
        private String name;

        @ApiModelProperty(value = "패스워드")
        private String password;

        @ApiModelProperty(value = "닉네임")
        private String nickname;

    }
    @Getter
    @Setter
    @ApiModel("AccountDto_Delete")
    public static class Delete {
        @ApiModelProperty(value = "삭제할 회원 아이디")
        private Long id;
    }
}
