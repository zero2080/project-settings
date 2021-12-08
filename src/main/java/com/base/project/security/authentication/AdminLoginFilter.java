package com.base.project.security.authentication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class AdminLoginFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper objectMapper;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    Map<String, String> loginRequest;

    try {
      loginRequest = objectMapper.readValue(request.getInputStream(), new TypeReference<>() {
      });
    } catch (IOException e) {
      throw new AuthenticationServiceException("Invalid Login Request");
    }

    String loginId = loginRequest.get("username");
    String password = loginRequest.get("password");

    if (loginId == null) {
      loginId = "";
    }

    if (password == null) {
      password = "";
    }

    loginId = loginId.trim();

    UsernamePasswordAuthenticationToken authRequest =
        new UsernamePasswordAuthenticationToken(loginId, password);

    // Allow subclasses to set the "details" property
    setDetails(request, authRequest);

    return getAuthenticationManager().authenticate(authRequest);
  }
}
