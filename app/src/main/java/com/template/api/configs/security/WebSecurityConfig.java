package com.template.api.configs.security;

import com.template.api.security.support.RestAuthenticationEntryPoint;
import com.template.api.security.support.RestAuthenticationFailureHandler;
import com.template.api.security.support.RestAuthenticationSuccessHandler;
import com.template.api.security.support.RestLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;
  private final RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
  private final RestLogoutSuccessHandler restLogoutSuccessHandler;

  //private final AccountAuthenticationProvider accountAuthenticationProvider;

  private final RestTemplateBuilder restTemplateBuilder;


  @Bean
  @ConditionalOnMissingBean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(
        "/docs/index.html",
        "/vendors/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v2/api-docs",
        "/webjars/**",
        "/fonts/**");
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("*");
    configuration.addAllowedMethod("GET");
    configuration.addAllowedMethod("POST");
    configuration.addAllowedMethod("PUT");
    configuration.addAllowedMethod("DELETE");
    configuration.addAllowedMethod("OPTIONS");
    configuration.addAllowedHeader("Authorization");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/").permitAll()
        .antMatchers("/oauth/**", "/oauth2/callback", "/webjars/**").permitAll()
        .antMatchers(HttpMethod.POST, "/api/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/**").permitAll()
        .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()
        .antMatchers("/error").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(new OAuth2AccessDeniedHandler())
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .formLogin()
        .successHandler(restAuthenticationSuccessHandler)
        .failureHandler(restAuthenticationFailureHandler)
        .and()
        .logout()
        .logoutSuccessHandler(restLogoutSuccessHandler)
        .and()
        .csrf().disable()
        .cors();
  }

  /**
 * @return the rest template
   */
  @Bean
  @ConditionalOnMissingBean
  public RestTemplate restTemplate() {
    return restTemplateBuilder.build();
  }

}
