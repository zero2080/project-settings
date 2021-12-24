package com.hana.project.model.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeResponse {
  private String title;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
