package com.hana.project.controller;

import com.hana.project.model.dto.request.AdminChangePasswordRequest;
import com.hana.project.model.entity.AdminUser;
import com.hana.project.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@RequestMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

  private final ObjectMapper mapper;
  private final AdminService adminService;

  @PutMapping("/profile")
  public void updateProfile(@RequestBody AdminChangePasswordRequest password,
      @AuthenticationPrincipal AdminUser user) {
    adminService.updateProfile(password, user);
  }
/*
  @GetMapping("/notice")
  public List<NoticeResponse> getNotice() {
    return service.getNotice().stream().map(notice -> mapper.convertValue(notice, NoticeResponse.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/notice/{id}")
  public NoticeResponse getNotice(@PathVariable UUID id) throws NotFoundException {
    return mapper.convertValue(service.getNotice(id), NoticeResponse.class);
  }

  @PostMapping("/notice")
  public void writeNotice(@RequestBody NoticeRequest req) {
    service.writeNotice(req);
  }*/
}
