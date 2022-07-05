package com.template.api.apps.account.dto;

import com.template.api.apps.account.domain.Account;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountDtoMapper extends BaseDtoMapper<Account, AccountDto.Response, AccountDto.Create, AccountDto.Update> {
  AccountDtoMapper INSTANCE = Mappers.getMapper(AccountDtoMapper.class);

  List<AccountDto.Response> toResponses(List<Account> accounts);

  Account create(AccountDto.Create create);

  void update(AccountDto.Update update,@MappingTarget Account account);

  Account delete(AccountDto.Delete delete);
}


