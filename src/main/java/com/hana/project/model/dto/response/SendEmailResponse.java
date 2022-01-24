package com.hana.project.model.dto.response;

import com.hana.project.util.RestApiComponent.AbstractUnmappingResponseHolder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailResponse extends AbstractUnmappingResponseHolder {

  private String requestId;
  private int count;
}
