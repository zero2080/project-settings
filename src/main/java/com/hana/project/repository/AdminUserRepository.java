package com.hana.project.repository;

import com.hana.project.model.entity.AdminUser;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, UUID> {
  Optional<AdminUser> findByUsername(String username);
}
