package com.base.project.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AdminUserAuthenticationProvider extends DaoAuthenticationProvider {

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

  @Override
  protected Authentication createSuccessAuthentication(Object principal,
      Authentication authentication, UserDetails user) {
    AdminUserDetail userDetail = (AdminUserDetail) user;
    UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(userDetail.getAccount(), null, userDetail.getAuthorities());
    authResult.setDetails(authentication.getDetails());
    return authResult;
  }

  @Override
  @Transactional(noRollbackFor = BadCredentialsException.class)
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    try {
      super.additionalAuthenticationChecks(userDetails, authentication);
    } catch (BadCredentialsException e) {
      //패스워드 횟수 오류 등 점증 오류처리
      throw e;
    }
  }

  @Autowired
  public void setUserDetailsService(AdminUserDetailsService userDetailsService) {
    super.setUserDetailsService(userDetailsService);
  }
}
