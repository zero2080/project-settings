package com.base.project.exceptions.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommonErrorCode implements BaseErrorCode{
  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "C001", "잘못된 요청 파라미터입니다."),
  NOT_FOUND(HttpStatus.NOT_FOUND, "C002", "존재하지 않는 데이터입니다."),
  KEY_ALREADY_IN_USE(HttpStatus.CONFLICT, "C003", "고유키 중복 오류입니다."),
  INVALID_REFERENCE(HttpStatus.BAD_REQUEST, "C004", "잘못된 참조입니다."),

  SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "서버에 문제가 발생했습니다."),
  EXTERNAL_INTEGRATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "외부 연동에 실패했습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;
}
