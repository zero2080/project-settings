package com.hana.project.support;

import com.hana.project.model.type.CommonRole;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockAdminContextFactory.class)
public @interface WithMockAdmin {
  String id() default "00000001-a000-b000-c000-d00000000000";

  String username() default "admin";

  String password() default "admin";

  CommonRole role() default CommonRole.ROLE_ADMIN;
}
