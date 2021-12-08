package com.base.project.model.entity;

import com.base.project.model.dto.request.NoticeRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
  @Id
  @GeneratedValue
  @Type(type="uuid-char")
  private UUID id;
  private String title;
  @Column(columnDefinition = "LONGTEXT")
  private String content;
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Notice(NoticeRequest req){
    this.title=req.getTitle();
    this.content=req.getContent();
  }
}

