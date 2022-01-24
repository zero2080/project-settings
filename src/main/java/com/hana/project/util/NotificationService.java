package com.hana.project.util;

import com.hana.project.exceptions.BaseException;
import com.hana.project.exceptions.type.CommonErrorCode;
import com.hana.project.model.dto.request.SendEmailRequest;
import com.hana.project.model.dto.response.SendEmailResponse;
import com.hana.project.model.type.EmailTemplate;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class NotificationService {

  private final NcloudConfigurationProperties properties;
  private final RestApiComponent restApiComponent;

  public NotificationService(NcloudConfigurationProperties properties) {
    this.properties = properties;
    this.restApiComponent = new RestApiComponent();
  }

  private HttpHeaders createHeaders(HttpMethod method, String path) {
    String timeStampStr = String.valueOf(System.currentTimeMillis());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-ncp-apigw-timestamp", timeStampStr);
    headers.set("x-ncp-iam-access-key", properties.getAccessKey());
    headers.set("x-ncp-apigw-signature-v2", makeSignature(method.name(), path, timeStampStr));

    return headers;
  }

  private String makeSignature(String method, String url, String timestamp) {
    String plainSignature =
        String.join("\n",
            String.join(" ", method, url), timestamp, properties.getAccessKey());

    SecretKeySpec signingKey =
        new SecretKeySpec(properties.getSecretKey().getBytes(StandardCharsets.UTF_8), "HmacSHA256");

    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(signingKey);
      byte[] rawHmac = mac.doFinal(plainSignature.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(rawHmac);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      log.error("Ncloud signature creation failed: ", e);
      throw BaseException.from(CommonErrorCode.SERVER_ERROR);
    }
  }

  public void send(EmailTemplate template, Map<String, Object> parameters) {
    SendEmailRequest requestBody = SendEmailRequest.of(template, properties.getEmail().getAddress(),
        parameters);

    URI uri = UriComponentsBuilder
        .fromUri(properties.getEmail().getUrl())
        .path(properties.getEmail().getPath())
        .build()
        .toUri();

    RequestEntity<SendEmailRequest> request = RequestEntity.post(uri)
        .headers(createHeaders(HttpMethod.POST, properties.getEmail().getPath()))
        .body(requestBody);

    try {
      restApiComponent.call(request, SendEmailResponse.class);
    } catch (Throwable t) {
      log.error("Send Email Failed", t);
      throw BaseException.from(CommonErrorCode.EXTERNAL_INTEGRATION_ERROR);
    }
  }
}