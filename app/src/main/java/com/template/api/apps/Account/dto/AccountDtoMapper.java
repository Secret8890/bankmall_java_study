package com.template.api.apps.Account.dto;

import com.template.api.apps.Account.domain.Account;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface AccountDtoMapper extends BaseDtoMapper
      //  <Account, AccountDto.Response, AccountDto.Create, AccountDto.Update>
{

    AccountDtoMapper INSTANCE = Mappers.getMapper(AccountDtoMapper.class);
    AccountDto.Response toResponse(Account accounts);
    List<AccountDto.Response> toResponse(List<Account> accounts);
    Account create(AccountDto.Create create);
    void update(AccountDto.Update update, @MappingTarget Account account);

    Account delete(AccountDto.Delete delete);
}
