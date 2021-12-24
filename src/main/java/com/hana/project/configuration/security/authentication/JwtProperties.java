package com.hana.project.configuration.security.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

  private String secret;
  private int validity;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public int getValidity() {
    return validity;
  }

  public void setValidity(int validity) {
    this.validity = validity;
  }
}