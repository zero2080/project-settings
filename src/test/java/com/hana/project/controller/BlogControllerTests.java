package com.hana.project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.hana.BaseProjectApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class BlogControllerTests extends BaseProjectApplicationTest {

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
}
