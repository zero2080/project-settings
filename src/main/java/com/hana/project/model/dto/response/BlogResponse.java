package com.hana.project.model.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogResponse {

  private UUID id;
  private String thumb;
  private String title;
  private String type;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
