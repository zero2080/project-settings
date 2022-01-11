package com.hana.project.controller;

import com.hana.project.model.dto.request.BlogRequest;
import com.hana.project.model.dto.response.BlogResponse;
import com.hana.project.model.entity.Blog;
import com.hana.project.service.BlogService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/blog", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogController {

  private final BlogService service;

  private final ModelMapper mapper;

  @GetMapping
  public List<BlogResponse> getNotice() {
    return service.get()
        .stream().map(page ->
            mapper.map(page, BlogResponse.class)
        ).collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public BlogResponse getNotice(@PathVariable UUID id) throws NotFoundException {
    return mapper.map(service.get(id), BlogResponse.class);
  }

  @PostMapping
  public void writeNotice(@ModelAttribute BlogRequest request) {
    service.write(request);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    Blog blog = new Blog();
    blog.setId(id);
    service.delete(blog);
  }
}
