package com.hana.project.exceptions;

import com.hana.project.exceptions.type.BaseErrorCode;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{

  private final HttpStatus status;
  private final String errorCode;

  protected BaseException(BaseErrorCode errorCode) {
    this(null, errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
  }

  private BaseException(Throwable cause, HttpStatus status, String errorCode,
      String errorMessage) {
    super(errorMessage, cause);
    this.status = status;
    this.errorCode = errorCode;
  }

  public static BaseException from(BaseErrorCode errorCode) {
    return new BaseException(null,
        errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
  }

  public static BaseException of(BaseErrorCode errorCode, String message) {
    return new BaseException(null, errorCode.getStatus(), errorCode.getCode(), message);
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return getMessage();
  }

}
