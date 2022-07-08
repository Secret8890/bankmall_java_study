package com.template.api.apps.profile.domain;

import com.template.api.apps.profile.dto.ProFileDtoMapper;
import com.template.api.apps.profile.dto.ProfileDto;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "profile")

public class Profile extends BaseDomainWithId implements DomainWithMapper<ProfileDto.Response> {

    private String userId;

    private String subname;

    private String nickname;

    @Override
    public ProfileDto.Response toResponse() {
        return ProFileDtoMapper.INSTANCE.toResponse(this);
    }
}
