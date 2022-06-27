package com.template.api.configs.security;

import com.template.api.configs.property.AppProperties;
//import com.template.api.apps.reviews.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
@EnableConfigurationProperties(AppProperties.class)
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final AppProperties appProperties;
//  private final UserDetailsServiceImpl userDetailsService;

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .tokenStore(tokenStore())
        .accessTokenConverter(accessTokenConverter())
//        .userDetailsService(userDetailsService)
        .authenticationManager(authenticationManager)
    ;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.allowFormAuthenticationForClients();
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    AppProperties.Oauth oauthProperties = appProperties.getOauth();
    clients.inMemory()
        .withClient(oauthProperties.getClientId())
        .secret(passwordEncoder.encode(oauthProperties.getClientSecret()))
        .authorizedGrantTypes("password", "refresh_token", "client_credentials")
        .scopes("any")
        .accessTokenValiditySeconds(oauthProperties.getTokenValiditySeconds())
        .refreshTokenValiditySeconds(oauthProperties.getRefreshTokenValiditySeconds())
    ;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    CUserAuthConverter userTokenConverter = new CUserAuthConverter();
//    userTokenConverter.setUserDetailsService(userDetailsService);

    DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
    accessTokenConverter.setUserTokenConverter(userTokenConverter);

    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(appProperties.getOauth().getTokenSigningKey());
    converter.setAccessTokenConverter(accessTokenConverter);
    return converter;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

}
