package com.template.api.files.dto;

import com.template.api.files.domain.AttachFile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttachFileMapper {

  AttachFileMapper INSTANCE = Mappers.getMapper(AttachFileMapper.class);

  AttachFileDto.Response entityToResponse(AttachFile entity);

  AttachFileDto.Download entityToDownload(AttachFile entity);

}
