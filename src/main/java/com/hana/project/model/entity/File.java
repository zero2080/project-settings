package com.hana.project.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
public class File {

  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  private UUID id;
  @ManyToOne
  @JoinColumn(name = "collection_id")
  private Collection collection;
  private String type;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;


  public String getFullName() {
    return String.join("/", "https://hana-package.syopingbaeg.com",
        collection != null ? "collection" : "blog",
        String.format("%s.%s", id, type));
  }

}
