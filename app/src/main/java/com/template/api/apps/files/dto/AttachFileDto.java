package com.template.api.apps.files.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

/**
 * The type File dto.
 *
 * @author eomjeongjae
 * @since 2019 /10/15
 */
public class AttachFileDto {

  /**
   * The type Download.
   */
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

  /**
   * The type Response.
   */
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  @Builder
  @ApiModel("FileDto.Response")
  public static class Response {

    private Long id;

    private String serverPath;

    private String filename;

    private String extension;

    private String contentType;

    private Long size;

    @ApiModelProperty(hidden = true)
    private MediaType mediaType;

    private boolean isImage;

    private String serverFilePath;

    private LocalDateTime createdAt;

    @ApiModelProperty(value = "비밀 여부", example = "true", required = true)
    private boolean isSecret;

    private String link;

  }
}
