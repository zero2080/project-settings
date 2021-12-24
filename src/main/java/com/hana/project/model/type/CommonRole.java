package com.hana.project.model.type;

public enum CommonRole {
  ROLE_ADMIN(1,"관리자"),ROLE_VISITOR(2,"방문자");

  private final int code;
  private final String description;

  CommonRole(int code, String description){
    this.code=code;
    this.description=description;
  }

  public int getCode(){
      return code;
  }

  public String getDescription(){
    return description;
  }
}
