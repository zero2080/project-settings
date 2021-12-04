package com.base.project.repository;

import com.base.project.model.entity.AdminUser;
import com.base.project.security.authentication.AdminUserDetail;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, UUID> {
  Optional<AdminUserDetail> findByUsername(String username);
}
