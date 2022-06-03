package com.template.api.configs.security;

import com.template.api.security.support.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/api/**")
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
        .antMatchers(HttpMethod.POST, "/api/**").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/**").permitAll()
        .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()
        .antMatchers("/api/sy/common-codes/map").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new RestAuthenticationEntryPoint());
//        .accessDeniedHandler(new AccessDenineHandler());
  }
}
