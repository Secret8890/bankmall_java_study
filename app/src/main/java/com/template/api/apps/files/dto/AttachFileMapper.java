package com.template.api.apps.files.dto;

import com.template.api.apps.files.domain.AttachFile;
import org.mapstruct.factory.Mappers;

public interface AttachFileMapper {

    AttachFileMapper INSTANCE = Mappers.getMapper(AttachFileMapper.class);

    AttachFileDto.Response entityToResponse(AttachFile entity);

    AttachFileDto.Download entityToDownload(AttachFile entity);
}
