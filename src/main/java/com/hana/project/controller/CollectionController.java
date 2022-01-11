package com.hana.project.controller;

import com.hana.project.model.dto.request.CollectionRequest;
import com.hana.project.model.dto.response.CollectionResponse;
import com.hana.project.model.entity.Collection;
import com.hana.project.service.CollectionService;
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
public class CollectionController {

  private final CollectionService service;
  private final ModelMapper mapper;

  @GetMapping
  public List<CollectionResponse> getList() {
    return service.getList()
        .stream().map(page ->
            mapper.map(page, CollectionResponse.class)
        ).collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public CollectionResponse getOne(@PathVariable UUID id) throws NotFoundException {
    return mapper.map(service.getOne(id), CollectionResponse.class);
  }

  @PostMapping
  public void writeNotice(@ModelAttribute CollectionRequest request) {
    service.write(request.getFile(), mapper.map(request, Collection.class));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    Collection collection = new Collection();
    collection.setId(id);
    service.delete(collection);
  }
}
