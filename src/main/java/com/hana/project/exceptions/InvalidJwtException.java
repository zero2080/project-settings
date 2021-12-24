package com.hana.project.exceptions;

import com.hana.project.exceptions.type.CommonErrorCode;
import org.springframework.security.core.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {

  private final CommonErrorCode errorCode;

  public InvalidJwtException(CommonErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

  public CommonErrorCode getErrorCode() {
    return errorCode;
  }

}
