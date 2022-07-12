package com.template.api.apps.files.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;


public class AttachFileDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Download {

        private Response attachFile;
        private Resource resource;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Response{

        private Long id;

        @ApiModelProperty(value = "서버경로")
        private String serverPath;

        @ApiModelProperty(value = "이름")
        private String filename;

        @ApiModelProperty(value = "확대")
        private String extension;

        @ApiModelProperty(value = "컨텐츠 종류")
        private String contentType;

        @ApiModelProperty(value = "크기")
        private Long size;

        @ApiModelProperty(value = "타입", hidden = true)
        private MediaType mediaType;

        private  boolean isImage;

        @ApiModelProperty(value = "서버파일 경로")
        private String serverFilePath;

        @ApiModelProperty(value = "생성 시간")
        private LocalDateTime createdAt;

        @ApiModelProperty(value = "비밀 여부", example = "true", required = true)
        private boolean isSecret;

        @ApiModelProperty(value = "링크")
        private String link;
    }

}
