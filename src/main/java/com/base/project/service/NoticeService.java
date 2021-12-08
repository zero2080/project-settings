package com.base.project.service;

import com.base.project.model.dto.request.NoticeRequest;
import com.base.project.model.entity.Notice;
import com.base.project.repository.NoticeRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
  private final NoticeRepository repository;

  public Notice getNotice(UUID id) throws NotFoundException{
    return repository.findById(id).orElseThrow(NotFoundException::new);
  }

  public void writeNotice(NoticeRequest req) {
    repository.saveAndFlush(new Notice(req));
  }

  public List<Notice> getNotice() {
    return repository.findAll();
  }
}
