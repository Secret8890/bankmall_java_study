package com.template.api.apps.accounts.dto;

import com.template.api.apps.accounts.domain.Account;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountDtoMapper extends BaseDtoMapper<Account, AccountDto.Response, AccountDto.Create, AccountDto.Update> {
  AccountDtoMapper INSTANCE = Mappers.getMapper(AccountDtoMapper.class);

  List<AccountDto.Response> toResponses(List<Account> accounts);

}