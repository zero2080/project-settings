package com.base.project.security.authentication;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JsonLoginConfigurer<T extends HttpSecurityBuilder<T>> extends
    AbstractAuthenticationFilterConfigurer<T, JsonLoginConfigurer<T>, AdminLoginFilter> {

  public JsonLoginConfigurer( AdminLoginFilter authenticationFilter ) {
    super(authenticationFilter, "/admin/login");
    getAuthenticationFilter().setUsernameParameter("username");
    getAuthenticationFilter().setPasswordParameter("password");
  }

  @Override
  protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
    return new AntPathRequestMatcher(loginProcessingUrl, "POST");
  }

  @Override
  public void init(T http) throws Exception{
    super.init(http);
    DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = http
        .getSharedObject(DefaultLoginPageGeneratingFilter.class);

    if (loginPageGeneratingFilter != null && !isCustomLoginPage()) {
      loginPageGeneratingFilter.setFormLoginEnabled(true);
      loginPageGeneratingFilter.setUsernameParameter("username");
      loginPageGeneratingFilter.setPasswordParameter("password");
      loginPageGeneratingFilter.setLoginPageUrl(getLoginPage());
      loginPageGeneratingFilter.setFailureUrl(getFailureUrl());
      loginPageGeneratingFilter.setAuthenticationUrl(getLoginProcessingUrl());
      loginPageGeneratingFilter.setLogoutSuccessUrl("/login");
    }
  }
}
