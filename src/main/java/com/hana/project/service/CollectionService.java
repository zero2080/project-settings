package com.hana.project.service;

import com.hana.project.model.entity.Collection;
import com.hana.project.model.entity.File;
import com.hana.project.repository.CollectionRepository;
import com.hana.project.repository.FileRepository;
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
  private final FileRepository fileRepository;
  private final AwsS3Service s3Service;

  public List<Collection> getList() {
    return repository.findTop8ByOrderByCreatedAtDesc();
  }

  public Collection getOne(UUID id) throws NotFoundException {
    Collection collection = repository.findById(id).orElseThrow(NotFoundException::new);
    File file = fileRepository.findByCollection(collection);
    collection.setThumb(file.getFullName());
    return collection;
  }

  public void write(MultipartFile file, Collection request) {
    //글작성
    repository.saveAndFlush(request);
    File thumb = new File();
    thumb.setCollection(request);
    thumb.setType(fileTypeGetter(file));
    fileRepository.saveAndFlush(thumb);

    s3Service.upload(file, thumb.getFullName());
  }

  public void delete(Collection collection) {
    repository.delete(collection);
  }

  private String fileTypeGetter(MultipartFile file) {
    return file.getOriginalFilename()
        .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
  }
}
