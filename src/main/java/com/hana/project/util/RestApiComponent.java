package com.hana.project.util;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestApiComponent {

  private RestTemplate factory;

  public RestApiComponent() {
    create();
  }

  private void create() {
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setConnectionRequestTimeout(10_000);
    requestFactory.setConnectTimeout(5_000);
    requestFactory.setReadTimeout(10_000);
    HttpClient client = HttpClientBuilder.create()
        .setMaxConnTotal(100)
        .setMaxConnPerRoute(50)
        .setConnectionTimeToLive(10_000, TimeUnit.MILLISECONDS)
        .build();

    requestFactory.setHttpClient(client);

    RestTemplate restTemplate = new RestTemplate(requestFactory);
    restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

    factory = new RestTemplate(requestFactory);
    log.debug("RestTemplate Creation Success");
  }

  public <T, R> R call(RequestEntity<T> request, Class<R> responseType) {
    ApiRequestWrapper<T, R> wrapper = new ApiRequestWrapper<>(request, responseType);
    try {
      ResponseEntity<R> response = factory.exchange(request, responseType);
      log.info("Request finished Successfully: {}", wrapper.withResponse(response));
      return response.getBody();
    } catch (ResourceAccessException accessException) {
      log.info("Request Not Accessible: {}", wrapper.withError(accessException));
      throw accessException;
    } catch (HttpStatusCodeException restException) {
      log.info("Rest Request Failed: {}", wrapper.withError(restException));
      throw restException;
    }
  }

  public <T, R> R call(RequestEntity<T> request, ParameterizedTypeReference<R> responseType) {
    ApiRequestWrapper<T, R> wrapper = new ApiRequestWrapper<>(request, responseType);
    try {
      ResponseEntity<R> response = factory.exchange(request, responseType);
      log.info("Request finished Successfully: {}", wrapper.withResponse(response));
      return response.getBody();
    } catch (ResourceAccessException accessException) {
      log.info("Request Not Accessible: {}", wrapper.withError(accessException));
      throw accessException;
    } catch (HttpStatusCodeException restException) {
      log.info("Rest Request Failed: {}", wrapper.withError(restException));
      throw restException;
    }
  }

  @Getter
  public static class ApiRequestWrapper<T, R> {

    private final RequestEntity<T> request;
    private HttpStatus responseStatus;
    private HttpHeaders responseHeader = new HttpHeaders();
    private R responseBody;
    private String errorMessage;
    private RuntimeException exception;

    public ApiRequestWrapper(RequestEntity<T> request, Class<R> returnType) {
      this.request = request;
    }

    public ApiRequestWrapper(RequestEntity<T> request, ParameterizedTypeReference<R> returnType) {
      this.request = request;
    }

    public ApiRequestWrapper<T, R> withResponse(ResponseEntity<R> response) {
      this.responseStatus = response.getStatusCode();
      this.responseBody = response.getBody();
      this.responseHeader = response.getHeaders();
      return this;
    }

    public ApiRequestWrapper<T, R> withError(HttpStatusCodeException ex) {
      this.responseStatus = ex.getStatusCode();
      this.errorMessage = ex.getMessage();
      this.exception = ex;
      return this;
    }

    public ApiRequestWrapper<T, R> withError(RestClientException ex) {
      this.errorMessage = ex.getMessage();
      this.exception = ex;
      return this;
    }

    public String getUri() {
      return request.getUrl().toString();
    }

    public HttpMethod getMethod() {
      return request.getMethod();
    }

    public HttpHeaders getRequestHeaders() {
      return request.getHeaders();
    }

    public T getRequestBody() {
      return request.getBody();
    }

    public String getResponseHeader(String key) {
      return responseHeader.getOrDefault(key, Collections.emptyList())
          .stream()
          .findFirst()
          .orElse(null);
    }

    public boolean isSuccess() {
      return exception == null;
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", ApiRequestWrapper.class.getSimpleName() + "[", "]")
          .add("uri='" + getUri() + "'")
          .add("method=" + getMethod())
          .add("requestHeaders=" + getRequestHeaders())
          .add("requestBody=" + getRequestBody())
          .add("responseStatus=" + responseStatus.value())
          .add("responseBody=" + responseBody)
          .add("errorMessage='" + errorMessage + "'")
          .add("exception=" + exception)
          .toString();
    }
  }

  public static abstract class AbstractUnmappingResponseHolder {

    private final Map<String, Object> unmappedValues = new LinkedHashMap<>();

    @JsonAnyGetter
    protected Map<String, Object> getUnmappedValues() {
      return unmappedValues;
    }

    @JsonAnySetter
    public void setUnmappedValues(String key, Object value) {
      this.unmappedValues.put(key, value);
    }
  }
}
