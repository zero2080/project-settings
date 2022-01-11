package com.hana.project.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CollectionRequest {

  private MultipartFile file;
  private String title;
  private String type;
}
