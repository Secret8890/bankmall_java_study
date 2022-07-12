package com.template.api.apps.files.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties("file")
public class FileProperties {

    @ApiModelProperty(value = "다시 보내기")
    private String fileRedirect;

    @ApiModelProperty(value = "파일 보내기")
    private String xSendFile;

    @ApiModelProperty(value = "확장자")
    private String extensionHeic;

    @ApiModelProperty(value = "확장 변환")
    private String extensionConvert;

    @ApiModelProperty(value = "확장 합성")
    private String extensionComposite;
}
