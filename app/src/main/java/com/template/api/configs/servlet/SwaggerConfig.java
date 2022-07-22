package com.template.api.configs.servlet;

import static java.util.Collections.singletonList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import com.fasterxml.classmate.TypeResolver;
import com.template.api.configs.property.AppProperties;
import com.template.api.security.account.UserPrincipal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Profile({"!prod", "!prod*"})
@EnableSwagger2
@RequiredArgsConstructor
@Configuration
@ComponentScan(basePackages = "com.template.api")
public class SwaggerConfig {

  private final TypeResolver typeResolver;
  private final AppProperties appProperties;


  private ApiKey apiKey(){
    return new ApiKey("APIKey","Authorization","header");
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .host(appProperties.getHost())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/api/**"))
        .build()
        .directModelSubstitute(LocalDate.class, String.class)
        //.directModelSubstitute(YearMonth.class, String.class)
        .genericModelSubstitutes(ResponseEntity.class)
        .alternateTypeRules(
            newRule(
                typeResolver.resolve(DeferredResult.class,
                    typeResolver.resolve(ResponseEntity.class, WildcardType.class)
                ),
                typeResolver.resolve(WildcardType.class)
            )
        )
        .useDefaultResponseMessages(false)
        .ignoredParameterTypes(
            Authentication.class,
            UserPrincipal.class,
            AuthenticationPrincipal.class,
            OAuth2Authentication.class,
            HttpServletRequest.class,
            HttpServletResponse.class
        )
        .securitySchemes(Arrays.asList(oauthSecurityScheme()))
        .securityContexts(Arrays.asList(oauthSecurityContext()))
        .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
        appProperties.getName(),
        "",
        "",
        "",
        null,
        "",
        "",
        Collections.emptyList()
    );
  }

  @Bean
  public SecurityConfiguration securityConfiguration() {
    return SecurityConfigurationBuilder.builder()
        .clientId(appProperties.getOauth().getClientId())
        .clientSecret(appProperties.getOauth().getClientSecret())
        .scopeSeparator("password refresh_token client_credentials")
        .useBasicAuthenticationWithAccessCodeGrant(true)
        .build();
  }

  private SecurityScheme oauthSecurityScheme() {
    GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("/oauth/token");
    return new OAuthBuilder().name("spring_oauth")
        .grantTypes(singletonList(grantType))
        .build();
  }

  private SecurityContext oauthSecurityContext() {
    return SecurityContext.builder()
        .securityReferences(
            singletonList(new SecurityReference("spring_oauth", scopes()))
        )
        .forPaths(PathSelectors.ant("/api/**"))
        .build();
  }

  private SecurityContext jwtContext() {
    return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();
  }



  private AuthorizationScope[] scopes() {
    return new AuthorizationScope[]{
        new AuthorizationScope("any", "for any operations")};
  }

  private List<SecurityReference> defaultAuth() {
    final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
    return Collections.singletonList(new SecurityReference("APIKey", authorizationScopes));
  }


}
