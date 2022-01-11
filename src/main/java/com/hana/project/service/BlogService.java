package com.hana.project.service;

import com.hana.project.model.dto.request.BlogRequest;
import com.hana.project.model.entity.Blog;
import com.hana.project.repository.BlogRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

  private final BlogRepository repository;

  public Blog get(UUID id) throws NotFoundException {
    return repository.findById(id).orElseThrow(NotFoundException::new);
  }

  public void write(BlogRequest request) {
    //TODO: S3이미지 업로드
    //TODO: 업로드된 이미지링크 entity객체 세팅

    String tempFileLink = "https://s3.ap-northeast-2.amazonaws.com/hana-package.syopingbaeg.com/blog/test_tumb.png";

    request.getBlog().setThumb(tempFileLink);

    repository.saveAndFlush(request.getBlog());
  }

  public void delete(Blog blog) {
    repository.delete(blog);
  }

  public List<Blog> get() {
    return repository.findTop8ByOrderByCreatedAtDesc();
  }

}
