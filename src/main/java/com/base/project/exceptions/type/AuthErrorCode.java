package com.base.project.exceptions.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthErrorCode implements BaseErrorCode{
  BAD_CREDENTIAL(HttpStatus.UNAUTHORIZED, "A001", "아이디 혹은 비밀번호가 잘못되었습니다."),
  FARM_ACCOUNT_LOCKED(HttpStatus.UNAUTHORIZED, "A002", "비밀번호 오류 허용 횟수 초과로 계정이 잠금 처리되었습니다."),
  TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A003", "토큰이 만료되었습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A004", "잘못된 토큰입니다."),
  FORBIDDEN(HttpStatus.FORBIDDEN, "A005", "접근권한이 없습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;
}
