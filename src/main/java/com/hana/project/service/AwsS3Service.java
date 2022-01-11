package com.hana.project.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hana.project.exceptions.BaseException;
import com.hana.project.exceptions.type.CommonErrorCode;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
@RequiredArgsConstructor
public class AwsS3Service {

  private final AmazonS3Client awsS3;
  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  public void upload(MultipartFile file, String filePath) {
    if (!awsS3.doesBucketExistV2(bucketName)) {
      awsS3.createBucket(bucketName);
    }

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());

    try {
      PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath,
          file.getInputStream(), metadata);
      AccessControlList acl = new AccessControlList();
      acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
      putObjectRequest.setAccessControlList(acl);
      awsS3.putObject(putObjectRequest);
    } catch (IOException e) {
      log.error("File upload failed", e);
      throw BaseException.from(CommonErrorCode.SERVER_ERROR);
    }
  }

}
