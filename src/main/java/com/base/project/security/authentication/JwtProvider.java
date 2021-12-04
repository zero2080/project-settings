package com.base.project.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final Algorithm ALGORITHM = Algorithm.HMAC512("package");
  private final String TOKEN_PREFIX = "Bearer ";

  public String generateToken(Map<String, String> admin) {
    return JWT.create().withSubject(admin.get("origin"))
        .withExpiresAt(Date.from(Instant.now().plusSeconds(180L)))
        .withClaim("ROLE", admin.get("role")).withClaim("adminId", admin.get("id")).sign(ALGORITHM);
  }

  public DecodedJWT parseToken(String jwt) {
    return JWT.require(ALGORITHM)
        .build()
        .verify(jwt.replaceFirst(TOKEN_PREFIX, ""));
  }
}
