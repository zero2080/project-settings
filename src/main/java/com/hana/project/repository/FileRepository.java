package com.hana.project.repository;

import com.hana.project.model.entity.Collection;
import com.hana.project.model.entity.File;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, UUID> {

  File findByCollection(Collection collection);
}
