package com.base.project.security;

import com.base.project.domain.entity.Customer;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetail extends User {
  private Customer account;
  public UserDetail(Customer account){
    super(
          account.getEmail(),
          account.getPassword(),
        true,
        true,
        true,
        true,
        Collections.singleton(new SimpleGrantedAuthority(account.getRole().name()))
    );
    this.account = account;
  }

  public Customer getAccount(){
    return this.account;
  }
}
