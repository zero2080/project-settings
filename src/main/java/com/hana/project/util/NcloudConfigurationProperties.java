package com.hana.project.util;

import java.net.URI;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class NcloudConfigurationProperties {

  private String accessKey;
  private String secretKey;
  private URI url;
  private String path;
  private Email email;

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  @Getter
  @Setter
  public static class Email {

    @NotNull
    private URI url;

    @NotEmpty
    private String path;

    @NotEmpty
    private String address;
  }
}