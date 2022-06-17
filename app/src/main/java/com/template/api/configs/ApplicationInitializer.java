package com.template.api.configs;

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


  @Value("${app.default_admin.username}")
  private String username;

  @Value("${app.default_admin.password}")
  private String password;

  @Override
  public void run(ApplicationArguments args) {
    log.info("Application startup complete.");
//    passwordEncoder.encode(password);

  }


}
