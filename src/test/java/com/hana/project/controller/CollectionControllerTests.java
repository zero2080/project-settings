package com.hana.project.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hana.BaseProjectApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

public class CollectionControllerTests extends BaseProjectApplicationTest {

  private String API_PATH = "/blog";

  @Test
  public void getBlogList_success() throws Exception {

    //When
    ResultActions result = mockMvc.perform(
        get(API_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
    );

    //Then
    result.andDo(System.out::println);

  }

  @Test
  public void uploadImage_success() throws Exception {
    // Given
    MockMultipartFile mockFile1 = new MockMultipartFile(
        "file", "file1.jpg", "image/jpeg", "File content".getBytes());

    // When
    ResultActions result = mockMvc.perform(
        fileUpload(API_PATH)
            .file(mockFile1)
            .queryParam("title", "상품명인것")
            .queryParam("type", "쇼핑백")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .accept(MediaType.APPLICATION_JSON));

    // Then
    result.andExpect(status().isOk());

    // Documentation
  }
}
