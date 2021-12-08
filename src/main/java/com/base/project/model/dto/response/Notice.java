package com.base.project.model.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notice {
  private String title;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
