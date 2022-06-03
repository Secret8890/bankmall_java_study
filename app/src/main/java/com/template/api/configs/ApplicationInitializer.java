package com.template.api.configs;

import com.template.api.apps.accounts.domain.Account;
import com.template.api.apps.accounts.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements ApplicationRunner {

  private final PasswordEncoder passwordEncoder;

  private final AccountRepository accountRepository;

  @Value("${app.default_admin.username}")
  private String username;

  @Value("${app.default_admin.password}")
  private String password;

  @Override
  public void run(ApplicationArguments args) {
    log.info("Application startup complete.");

    try {
      if (username != null && !username.isEmpty()
          && accountRepository.countByUserId(username) <= 0) {
        Account user = new Account();
        //users.setType(AccountType.ADMIN);
        user.setUserId(username);
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password));

        accountRepository.save(user);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }

//    passwordEncoder.encode(password);

  }


}
