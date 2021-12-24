package com.hana.project.configuration.security.authentication;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JsonLoginConfigurer<T extends HttpSecurityBuilder<T>> extends
    AbstractAuthenticationFilterConfigurer<T, JsonLoginConfigurer<T>, AbstractAuthenticationProcessingFilter> {

  public JsonLoginConfigurer(AbstractAuthenticationProcessingFilter authenticationFilter,
      String defaultLoginProcessingUrl) {
    super(authenticationFilter, defaultLoginProcessingUrl);
  }

  @Override
  protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
    return new AntPathRequestMatcher(loginProcessingUrl, "POST");
  }
}