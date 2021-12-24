package com.hana.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.BaseProjectApplicationTest;
import com.hana.project.support.WithMockAdmin;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminControllerTests extends BaseProjectApplicationTest {
  private static String API_PATH = "/admin";
  private final ObjectMapper mapper = new ObjectMapper();
  @MockBean
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("관리자 로그인 - 성공")
  @Order(1)
  public void adminLogin_success() throws Exception {
    //Given
    Map<String,String> requestBody = Map.of("username","admin","password","admin");

    when(passwordEncoder.matches(eq("admin"), any(String.class)))
        .thenReturn(true);

    //When
    ResultActions result = mockMvc.perform(
        post(API_PATH+"/authenticate")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(requestBody))
    );

    //Then
    result.andExpect(status().isOk())
        .andExpect(
            jsonPath("$.accessToken",is(notNullValue()))
        );

    // Documentation
    result.andDo(document("admin/profile/login",
        requestFields(
            fieldWithPath("username").description("로그인 ID"),
            fieldWithPath("password").description("비밀번호")
        ),
        responseFields(
            fieldWithPath("accessToken").type(STRING).description("JWT")
        )));
  }

  @Test
  @WithMockAdmin
  @DisplayName("관리자 계정 비밀번호 변경 - 성공")
  @Order(2)
  public void adminPasswordChange_success() throws Exception{
    //Given
    Map<String,String> changePassword = Map.of("before","admin","after", "changed");

    //When
    ResultActions result = mockMvc.perform(
        put(API_PATH+"/profile")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(changePassword))
    );

    //Then
    result.andExpect(status().isOk());

    //Documentation
    result.andDo(document("admin/profile/change-password",
        requestFields(
            fieldWithPath("before").description("이전 비밀번호"),
            fieldWithPath("after").description("변경할 비밀번호")
        )));
  }
}
