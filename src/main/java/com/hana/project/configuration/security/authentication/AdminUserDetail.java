package com.hana.project.configuration.security.authentication;

import com.hana.project.model.entity.AdminUser;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AdminUserDetail extends User {

  private AdminUser account;

  public AdminUserDetail(AdminUser account) {
    super(account.getUsername(), account.getPassword(), true, true, true,
        true,
        Collections.singleton(new SimpleGrantedAuthority(account.getRole().name())));
    this.account = account;
  }

  public AdminUser getAccount() {
    return account;
  }

  public static AdminUserDetail of(AdminUser account){
    return new AdminUserDetail(account);
  }
}
