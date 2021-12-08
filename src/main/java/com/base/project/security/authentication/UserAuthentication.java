package com.base.project.security.authentication;

import com.base.project.model.entity.AdminUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

  public UserAuthentication(AdminUserDetail principal) {
    super(principal.getAccount(), null, principal.getAuthorities());
  }

  @Override
  public String getName() {
    if (getPrincipal() instanceof AdminUser) {
      return ((AdminUser) getPrincipal()).getId().toString();
    } else if (getPrincipal() instanceof String) {
      return getPrincipal().toString();
    }
    return super.getName();
  }

  @Override
  public WebAuthenticationDetails getDetails() {
    return (WebAuthenticationDetails) super.getDetails();
  }
}
