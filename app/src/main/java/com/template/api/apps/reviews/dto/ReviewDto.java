package com.template.api.apps.reviews.dto;

import com.template.api.utils.dtos.PagableDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ReviewDto {

    @Getter
    @Setter
    public static class Request extends PagableDto.Request {
        private String keyword;
    }

    @Getter
    @Setter
    public static class Create {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class Response {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class Update {
        private String id;
        private String title;
        private String content;
    }
}
