package com.base.project.controller;

import com.base.project.model.entity.AdminUser;
import com.base.project.model.type.CommonRole;
import com.base.project.repository.AdminUserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BeginController {

  private final AdminUserRepository repository;

  @GetMapping("/hi")
  public Map<String,String> hi(){
    return Map.of("message","hello");
  }

  @PostMapping("/hello/{id}")
  public Map<String,String> hello(@PathVariable String id) throws IllegalArgumentException{
    if(!id.equals("developer")){
      throw new IllegalArgumentException();
    }
    return Map.of("message","hi");
  }

  @GetMapping("/admin")
  public AdminUser getAdmin(){
    repository.saveAndFlush(new AdminUser(null,"admin", new BCryptPasswordEncoder().encode("admin"),
        CommonRole.ROLE_ADMIN));
    return repository.findByUsername("admin").get();
  }
}
