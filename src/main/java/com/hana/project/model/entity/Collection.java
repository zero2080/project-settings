package com.hana.project.model.entity;

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
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Collection {

  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  private UUID id;
  @Type(type = "uuid-char")
  private UUID thumb;
  private String title;
  private String type;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  public String getThumbPath() {
    if (getThumb() == null) {
      return null;
    }
    return String.join("/", "collection", getId().toString(), thumb.toString());
  }

}

