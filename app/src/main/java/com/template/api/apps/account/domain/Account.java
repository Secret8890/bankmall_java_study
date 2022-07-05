package com.template.api.apps.account.domain;

import com.template.api.apps.account.dto.AccountDto;
import com.template.api.apps.account.dto.AccountDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "account")

public class Account extends BaseDomainWithId implements DomainWithMapper<AccountDto.Response> {

    private String userId;

    private String password;

    private String name;

    private String nickname;

    @Override
    public AccountDto.Response toResponse() {
        return AccountDtoMapper.INSTANCE.toResponse(this);
    }
}
