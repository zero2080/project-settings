package com.hana.project.exceptions;

import com.hana.project.exceptions.type.AuthErrorCode;

public class ForbiddenException extends BaseException {
  public ForbiddenException() {
    super(AuthErrorCode.FORBIDDEN);
  }

}
