package com.hana.project.model.dto.request;

import com.hana.project.model.entity.Blog;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BlogRequest {

  private MultipartFile file;
  private Blog blog;
}
