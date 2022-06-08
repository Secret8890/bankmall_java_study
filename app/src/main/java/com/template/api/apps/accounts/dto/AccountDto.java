package com.template.api.apps.accounts.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.template.api.apps.accounts.domain.Account;
import com.template.api.utils.dtos.PagableDto;
import com.template.api.utils.interfaces.BaseDateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountDto {

    //region Default DTO

    @Getter
    @Setter
    @ApiModel("AccountDto_Request")
    public static class Request extends PagableDto.Request {

        @ApiModelProperty(value = "검색어", position = 2)
        private String keyword;

    }

    @Getter
    @Setter
    @ApiModel("AccountDto_Response")
    public static class Response {

        @ApiModelProperty(value = "회원관리번호")
        private Long id;

        @ApiModelProperty(value = "사용자 id")
        private String userId;

        @ApiModelProperty(value = "사용자 명")
        private String name;

        @ApiModelProperty(value = "생성시간")
        @BaseDateTimeFormat
        private LocalDateTime createdAt;

        @ApiModelProperty(value = "수정시간")
        @BaseDateTimeFormat
        private LocalDateTime updatedAt;
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

        @ApiModelProperty(value = "생성시간")
        @BaseDateTimeFormat
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    @ApiModel("AccountDto_Update")
    public static class Update {
    }

    //endregion


}
