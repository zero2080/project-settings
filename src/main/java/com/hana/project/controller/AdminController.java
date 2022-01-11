package com.hana.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.project.model.dto.request.AdminChangePasswordRequest;
import com.hana.project.model.entity.AdminUser;
import com.hana.project.service.AdminService;
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

}
