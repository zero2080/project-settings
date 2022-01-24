package com.hana.project.model.type;

import lombok.Getter;

@Getter
public enum CommonRole {
  ROLE_ADMIN(1, "관리자"),
  ROLE_VISITOR(2, "방문자");

  private final int code;
  private final String description;

  CommonRole(int code, String description) {
    this.code = code;
    this.description = description;
  }

}
