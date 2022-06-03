package com.template.api.apps.accounts.service;

import com.google.common.collect.Lists;
import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.accounts.domain.AccountRepository;
import com.template.api.security.account.AccountUser;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        Account user = accountRepository.findByUserId(arg0).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }

        List<String> authorities = Lists.newArrayList();
        authorities.add("ROLE_USER");

        return new AccountUser(
                user.getUserId(),
                user.getPassword(),
                authorities
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }

}
