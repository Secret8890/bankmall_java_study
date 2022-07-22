package com.template.api.apps.Account.domain;


import com.template.api.apps.Account.dto.AccountDto;
import com.template.api.apps.Account.dto.AccountDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "accountManagement ")
public class Account extends BaseDomainWithId implements DomainWithMapper
        //<AccountDto.Response>
{

    @Column(nullable = false, unique = true)
    private String userId;
    private String password;
    private String name;


    @Override
    public AccountDto.Response toResponse() {
        return AccountDtoMapper.INSTANCE.toResponse(this);
    }
}
