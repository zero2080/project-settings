package com.hana.project.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminChangePasswordRequest {
  private String before;
  private String after;
}
