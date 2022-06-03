package com.template.api.apps.accounts.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.template.api.apps.accounts.dto.AccountDto;
import com.template.api.apps.accounts.dto.AccountDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account extends BaseDomainWithId implements DomainWithMapper<AccountDto.Response> {

    @Column(nullable = false, unique = true)
    private String userId;

    private String password;

    private String name;

    @Override
    public AccountDto.Response toResponse() {
        return AccountDtoMapper.INSTANCE.toResponse(this);
    }
}
