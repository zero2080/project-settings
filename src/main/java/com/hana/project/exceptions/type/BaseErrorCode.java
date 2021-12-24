package com.hana.project.exceptions.type;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

  HttpStatus getStatus();
  String getCode();
  String getMessage();
}
