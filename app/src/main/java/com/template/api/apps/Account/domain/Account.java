package com.template.api.apps.Account.domain;


import com.template.api.apps.Account.dto.AccountDto;
import com.template.api.apps.Account.dto.AccountDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Account extends BaseDomainWithId implements DomainWithMapper<AccountDto.Response> {

    private String userId;
    private String password;
    private String name;

    @Override
    public AccountDto.Response toResponse() {
        return AccountDtoMapper.INSTANCE.toResponse(this);
    }
}
