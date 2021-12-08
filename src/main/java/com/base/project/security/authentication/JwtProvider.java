package com.base.project.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.base.project.model.entity.AdminUser;
import com.base.project.repository.AdminUserRepository;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final Algorithm ALGORITHM = Algorithm.HMAC512("package");
  private final String TOKEN_PREFIX = "Bearer ";

  public String generateToken(AdminUser admin) {
    return TOKEN_PREFIX + JWT.create().withSubject(admin.getId().toString())
        .withExpiresAt(Date.from(Instant.now().plusSeconds(1800L)))
        .withClaim("ROLE", admin.getRole().name()).withClaim("username", admin.getUsername())
        .sign(ALGORITHM);
  }

  public DecodedJWT parseToken(String jwt) {
    return JWT.require(ALGORITHM)
        .build()
        .verify(jwt.replaceFirst(TOKEN_PREFIX, ""));
  }
}
