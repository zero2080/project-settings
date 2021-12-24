package com.hana.project.support;

import com.hana.project.configuration.security.authentication.AdminUserDetail;
import com.hana.project.configuration.security.authentication.UserAuthentication;
import com.hana.project.model.entity.AdminUser;
import java.util.UUID;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockAdminContextFactory implements WithSecurityContextFactory<WithMockAdmin> {

  @Override
  public SecurityContext createSecurityContext(WithMockAdmin annotation) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();

    AdminUser adminUser = new AdminUser();
    adminUser.setId(UUID.fromString(annotation.id()));
    adminUser.setUsername(annotation.username());
    adminUser.setPassword(new BCryptPasswordEncoder().encode(annotation.password()));
    adminUser.setRole(annotation.role());

    AdminUserDetail principal = new AdminUserDetail(adminUser);

    UserAuthentication auth = new UserAuthentication(principal);

    context.setAuthentication(auth);
    return context;
  }
}
