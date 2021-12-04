package com.base.project.security;

import com.base.project.model.entity.AdminUser;
import com.base.project.model.type.CommonRole;
import com.base.project.security.authentication.AdminLoginFilter;
import com.base.project.security.authentication.AdminUserAuthenticationProvider;
import com.base.project.security.authentication.AdminUserDetail;
import com.base.project.security.authentication.JsonLoginConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeginSecurity extends WebSecurityConfigurerAdapter {

  private final ObjectMapper objectMapper;
  private final AuthenticationHandler authenticationHandler;
  private final AdminUserAuthenticationProvider adminUserAuthenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors().disable()
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationHandler))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeRequests(req ->
            {
              try {
                req
                    .antMatchers(HttpMethod.GET, "/").permitAll()
                    .antMatchers(HttpMethod.GET, "/docs/*").permitAll()

                    .and().apply(new JsonLoginConfigurer<>(new AdminLoginFilter(objectMapper),
                        "/admin/authenticate"))
                    .successHandler(authenticationHandler)
                    .failureHandler(authenticationHandler);

              } catch (Exception e) {
                e.printStackTrace();
              }
            }
        );
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    adminUserAuthenticationProvider.setPasswordEncoder(encoder());
    auth.authenticationProvider(adminUserAuthenticationProvider);

  }

  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
