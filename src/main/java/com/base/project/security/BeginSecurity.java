package com.base.project.security;

import com.base.project.security.authentication.AdminLoginFilter;
import com.base.project.security.authentication.JsonLoginConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

@Configuration
@RequiredArgsConstructor
public class BeginSecurity extends WebSecurityConfigurerAdapter {

  private final ObjectMapper objectMapper;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors().disable()
        .csrf().disable()
        .authorizeRequests(req ->
            {
              try {
                req
                    .antMatchers(HttpMethod.GET, "/").permitAll()
                    .antMatchers(HttpMethod.GET,"/img/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/css/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/js/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/docs/*").permitAll()
//                    .and().apply(new JsonLoginConfigurer<>(new AdminLoginFilter(objectMapper),"/admin/login")).defaultSuccessUrl("/index.html")
                    .and().apply(
                        new JsonLoginConfigurer<>(new AdminLoginFilter(objectMapper)))
                    .and().logout().logoutSuccessUrl("/admin");

              } catch (Exception e) {
                e.printStackTrace();
              }
            }
        );
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("ha_enNna_en")
        .password(encoder().encode("package12!")).roles("ADMIN").roles("USER");
  }

  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
