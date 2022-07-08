package com.template.api.files.domain;

import com.template.api.files.dto.AttachFileDto;
import com.template.api.files.dto.AttachFileMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
public class AttachFile extends BaseDomainWithId implements DomainWithMapper {

  @Column(nullable = false)
  private String serverPath;

  @Column(nullable = false)
  private String filename;

  @Column(length = 20)
  private String extension;

  @Column(nullable = false)
  private String contentType;

  @Column
  private boolean isSecret = Boolean.FALSE;

  @Builder.Default
  private Long size = 0L;

  public void setFilename(String filename) {
    this.filename = filename;
    this.extension = AttachFile.extractExtension(filename);
  }

  @Transient
  @Override
  public AttachFileDto.Response toResponse() {
    return AttachFileMapper.INSTANCE.entityToResponse(this);
  }

  public static class AttachFileBuilder {

    public AttachFileBuilder filename(String filename) {
      this.filename = filename;
      this.extension = AttachFile.extractExtension(filename);
      return this;
    }
  }

  @Transient
  public boolean isImage() {
    return getMediaType().isCompatibleWith(MediaType.IMAGE_JPEG);
  }


  @Transient
  public boolean isGif() {
    return getMediaType().equals(MediaType.IMAGE_GIF);
  }

  @Transient
  public boolean isVideo() {
    return getMediaType().getType().startsWith("video");
  }

  @Transient
  public MediaType getMediaType() {
    MediaType mediaType = null;
    try {
      mediaType = MediaType.parseMediaType(this.contentType);
    } catch (InvalidMediaTypeException e) {
      log.error("Fail parse media type");
    }

    if (mediaType == null) {
      mediaType = MediaType.APPLICATION_OCTET_STREAM;
    }
    return mediaType;
  }

  @Transient
  public String getServerFilePath() {
    return this.serverPath
        + "/"
        + this.getId()
        + "/"
        + this.getServerFilename();
  }

  @Transient
  public String getServerFilename() {
    String serverFileName = "source";
    if (StringUtils.isNotEmpty(this.extension)) {
      serverFileName += "." + this.extension;
    }
    return serverFileName;
  }

  @Transient
  private static String extractExtension(String filename) {
    if (filename.contains(".")) {
      return filename.substring(filename.lastIndexOf(".") + 1);
    }
    return StringUtils.EMPTY;
  }

}
