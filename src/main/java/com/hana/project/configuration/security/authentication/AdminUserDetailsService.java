package com.hana.project.configuration.security.authentication;

import com.hana.project.model.entity.AdminUser;
import com.hana.project.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

  private final AdminUserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username)
        .map(AdminUserDetail::new)
        .orElseThrow(() -> new UsernameNotFoundException("Admin account cannot Found"));
  }
}