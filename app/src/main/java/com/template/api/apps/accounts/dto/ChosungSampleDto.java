package com.template.api.apps.accounts.dto;

import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChosungSampleDto {


    @Getter
    @Setter
    @ApiModel("Chosung_Request")
    public static class Request extends PagableDto.Request {

        @ApiModelProperty(value = "검색어", position = 1)
        private String keyword;

        @ApiModelProperty(value = "검색 필드명", position = 2)
        private String field;

    }


}
