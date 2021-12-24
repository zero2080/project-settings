package com.hana.project.configuration;

import com.hana.project.configuration.security.AuthenticationHandler;
import com.hana.project.configuration.security.authentication.AdminLoginFilter;
import com.hana.project.configuration.security.authentication.AdminUserAuthenticationProvider;
import com.hana.project.configuration.security.authentication.JsonLoginConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.project.configuration.security.authentication.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final ObjectMapper objectMapper;
  private final AuthenticationHandler authenticationHandler;
  private final AdminUserAuthenticationProvider adminUserAuthenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors().and()
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationHandler))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeRequests(req ->
            {
              try {
                req
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

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    adminUserAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    auth.authenticationProvider(adminUserAuthenticationProvider);
  }

  @Override
  public void configure(WebSecurity web ){
    web.ignoring().antMatchers("/h2-console/**");
  }
}
