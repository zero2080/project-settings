package com.base.project.exceptions;

import com.base.project.exceptions.type.AuthErrorCode;

public class ForbiddenException extends BaseException {
  public ForbiddenException() {
    super(AuthErrorCode.FORBIDDEN);
  }

}
