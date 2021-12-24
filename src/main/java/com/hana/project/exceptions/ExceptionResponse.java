package com.hana.project.exceptions;

import com.hana.project.exceptions.type.BaseErrorCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ExceptionResponse {
  private final String errorCode;
  private final String message;

  public ExceptionResponse(String code, String message) {
    this.errorCode = code;
    this.message = message;
  }

  public static ExceptionResponse from(BaseException e) {
    return ExceptionResponse.of(e.getErrorCode(), e.getErrorMessage());
  }

  public static ExceptionResponse from(BaseErrorCode errorCode) {
    return ExceptionResponse.of(errorCode.getCode(), errorCode.getMessage());
  }

  public static ExceptionResponse of(String code, String message) {
    return new ExceptionResponse(code, message);
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
