package com.base.project.security.authentication;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.base.project.exceptions.ExceptionResponse;
import com.base.project.exceptions.type.AuthErrorCode;
import com.base.project.exceptions.type.BaseErrorCode;
import com.base.project.repository.AdminUserRepository;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  protected final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

  private final JwtProvider jwtProvider;
  private final AdminUserRepository repository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String jwt = resolveToken(request);

    if (StringUtils.isNotEmpty(jwt)) {
      DecodedJWT decodedJWT;
      try {
        decodedJWT = jwtProvider.parseToken(jwt);
      } catch (TokenExpiredException e) {
        handleResponse(response, AuthErrorCode.TOKEN_EXPIRED);
        return;
      } catch (JWTVerificationException e) {
        handleResponse(response, AuthErrorCode.INVALID_TOKEN);
        return;
      }

      //JWT subject에 사용자 UUID 추출
      repository.findById(UUID.fromString(decodedJWT.getSubject()))
          .map(user -> new UserAuthentication(new AdminUserDetail(user)))
          .ifPresent(auth -> {
            auth.setDetails(authenticationDetailsSource.buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
          });
    }

    // 결국 token이 없거나 user data와 sync가 맞지 않으면 skip
    filterChain.doFilter(request, response);
    SecurityContextHolder.clearContext();
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private void handleResponse(HttpServletResponse response, BaseErrorCode errorCode)
      throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

    try (PrintWriter writer = response.getWriter()) {
      writer.write(ExceptionResponse.from(errorCode).toString());
      writer.flush();
    }
  }
}
