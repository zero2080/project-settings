package com.hana.project.model.type;

import lombok.Getter;

@Getter
public enum EmailTemplate {
  PACKAGE_MAKING_REQUEST(0, "hana-package-making-req_v1");

  private final int id;
  private final String templateName;

  EmailTemplate(int id, String templateName) {
    this.id = id;
    this.templateName = templateName;
  }
}
