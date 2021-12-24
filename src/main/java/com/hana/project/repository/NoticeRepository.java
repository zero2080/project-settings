package com.hana.project.repository;

import com.hana.project.model.entity.Notice;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, UUID> {

}
