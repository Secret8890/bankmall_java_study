package com.template.api.apps.profile.dto;

import com.template.api.apps.profile.domain.Profile;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProFileDtoMapper extends BaseDtoMapper<Profile, ProfileDto.Response, ProfileDto.Create, ProfileDto.Update> {

  ProFileDtoMapper INSTANCE = Mappers.getMapper(ProFileDtoMapper.class);

  List<ProfileDto.Response> toResponses (List<Profile> profiles);

  Profile create(ProfileDto.Create create);

  void update(ProfileDto.Update update,@MappingTarget  Profile profile);
}


