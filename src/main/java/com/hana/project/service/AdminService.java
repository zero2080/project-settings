package com.hana.project.service;

import com.hana.project.exceptions.BaseException;
import com.hana.project.exceptions.type.AuthErrorCode;
import com.hana.project.model.dto.request.AdminChangePasswordRequest;
import com.hana.project.model.entity.AdminUser;
import com.hana.project.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final AdminUserRepository repository;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public void updateProfile(AdminChangePasswordRequest password,AdminUser user){
    if(passwordEncoder.matches(password.getBefore(),user.getPassword())){
      user.setPassword(passwordEncoder.encode(password.getAfter()));
      repository.saveAndFlush(user);
    }else{
      throw BaseException.from(AuthErrorCode.FARM_ACCOUNT_LOCKED);
    }
  }
}
