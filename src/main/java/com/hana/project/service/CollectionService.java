package com.hana.project.service;

import com.hana.project.model.entity.Collection;
import com.hana.project.repository.CollectionRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CollectionService {

  private final CollectionRepository repository;
  private final AwsS3Service s3Service;

  public List<Collection> getList() {
    return repository.findTop8ByOrderByCreatedAtDesc();
  }

  public Collection getOne(UUID id) throws NotFoundException {
    return repository.findById(id).orElseThrow(NotFoundException::new);
  }

  public void write(MultipartFile file, Collection request) {
    //TODO: S3이미지 업로드
    UUID imageId = UUID.randomUUID();

    //TODO: 업로드된 이미지링크 entity객체 세팅
    request.setThumb(imageId);

    repository.saveAndFlush(request);
    s3Service.upload(file, request.getThumbPath());
  }

  public void delete(Collection collection) {
    repository.delete(collection);
  }
}
