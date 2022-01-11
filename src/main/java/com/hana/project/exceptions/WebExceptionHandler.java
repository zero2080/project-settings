package com.hana.project.exceptions;

import com.hana.project.exceptions.type.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {


  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Void> basicClientError(IllegalArgumentException e) {
    log.debug(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }


  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ExceptionResponse> handleAccessDeniedException(
      AccessDeniedException e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ExceptionResponse.from(new ForbiddenException()));
  }


  @ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<String> handleHttpStatusCodeException(HttpStatusCodeException e) {
    return ResponseEntity.status(e.getStatusCode())
        .body(e.getResponseBodyAsString());
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse exception(Throwable e) {
    log.error("Unknown Error", e);
    return ExceptionResponse.of(
        CommonErrorCode.SERVER_ERROR.getCode(), e.getMessage());
  }
}
