package com.template.api.configs.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Map;

public class CUserAuthConverter extends DefaultUserAuthenticationConverter {

  @Override
  public Authentication extractAuthentication(Map<String, ?> map) {
    try {
      return super.extractAuthentication(map);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new UsernamePasswordAuthenticationToken("anonymousUser", "N/A", null);
    }
  }
}
