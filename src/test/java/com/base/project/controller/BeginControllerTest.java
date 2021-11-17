package com.base.project.controller;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.STRING;
import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.base.BaseProjectApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class BeginControllerTest extends BaseProjectApplicationTest {
  @Test
  public void hi_success() throws Exception{
    // When
    ResultActions result = mockMvc.perform(
        get("/hi")
            .accept(MediaType.APPLICATION_JSON));

    // Then
    result.andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("hello")));

    // Documentation
    result.andDo(document("begin/hello-docs/hi/success",
        responseFields(
            fieldWithPath("message").type(STRING).description("메세지"))
    ));
  }

  @Test
  public void hello_success() throws Exception{
    // When
    ResultActions result = mockMvc.perform(
        post("/hello/{id}","developer")
            .accept(MediaType.APPLICATION_JSON));

    // Then
    result.andExpect(status().isOk())
        .andExpect(jsonPath("$.message",is("hi")));

    // Documentation
    result.andDo(document("begin/hello-docs/hello/success",
        pathParameters(
            parameterWithName("id").description("예제 값").attributes(format("STRING"))
        ),
//      리퀘스트 바디에 데이터를 넘길때
//        requestFields(
//            fieldWithPath("ticketId").type(JsonFieldType.STRING).description("보유 이용권 ID")
//                .attributes(format("UUID"))
//        ),
        responseFields(
            fieldWithPath("message").type(STRING).description("메세지"))));
  }

  @Test
  public void hello_failure() throws Exception{
    // When
    ResultActions result = mockMvc.perform(
        post("/hello/{id}","server")
            .accept(MediaType.APPLICATION_JSON));

    // Then
    result.andExpect(status().is4xxClientError());

    // Documentation
    result.andDo(document("begin/hello-docs/hello/error"));
  }
}
