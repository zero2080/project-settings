package com.base.project.controller;

import com.base.project.model.dto.request.NoticeRequest;
import com.base.project.model.dto.response.Notice;
import com.base.project.service.NoticeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@RequestMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

  private final ObjectMapper mapper;
  private final NoticeService service;

  @GetMapping("/notice")
  public List<Notice> getNotice() {
    return service.getNotice().stream().map(notice -> mapper.convertValue(notice, Notice.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/notice/{id}")
  public Notice getNotice(@PathVariable UUID id) throws NotFoundException {
    return mapper.convertValue(service.getNotice(id), Notice.class);
  }

  @PostMapping("/notice")
  public void writeNotice(@RequestBody NoticeRequest req) {
    service.writeNotice(req);
  }
}
