package com.hana.project.configuration.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hana.project.model.entity.AdminUser;
import java.time.Instant;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final JwtProperties PROPERTIES;
  private final Algorithm ALGORITHM;
  private final String TOKEN_PREFIX = "Bearer ";

  public JwtProvider(JwtProperties properties) {
    this.PROPERTIES = properties;
    this.ALGORITHM = Algorithm.HMAC512(properties.getSecret());
  }

  public String generateToken(AdminUser admin) {
    return TOKEN_PREFIX + JWT.create()
        .withSubject(admin.getId().toString())
        .withExpiresAt(Date.from(Instant.now().plusSeconds(PROPERTIES.getValidity())))
        .withClaim("role", admin.getRole().name())
        .withClaim("username", admin.getUsername())
        .sign(ALGORITHM);
  }

  public DecodedJWT parseToken(String jwt) {
    return JWT.require(ALGORITHM)
        .build()
        .verify(jwt.replaceFirst(TOKEN_PREFIX, ""));
  }
}
