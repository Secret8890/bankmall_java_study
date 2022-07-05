package com.template.api.apps.review.dto;

import com.template.api.utils.dtos.PagableDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ReviewDto {

    @Getter
    @Setter
    @ApiModel("Review_Request")
    public static class Request extends PagableDto.Request {
        private String keyword;
    }

    @Getter
    @Setter
    @ApiModel("Review_Create")

    public static class Create {

        private String title;
        private String content;

    }

    @Getter
    @Setter
    @ApiModel("Review_Response")

    public static class Response {
        private Long id;
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @ApiModel("Review_Update")

    public static class Update {
        private Long id;
        private String title;
        private String content;
    }
}
