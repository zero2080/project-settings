package com.base;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.mustache.Mustache;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.restdocs.templates.StandardTemplateResourceResolver;
import org.springframework.restdocs.templates.TemplateEngine;
import org.springframework.restdocs.templates.TemplateFormat;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.restdocs.templates.mustache.AsciidoctorTableCellContentLambda;
import org.springframework.restdocs.templates.mustache.MustacheTemplateEngine;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class BaseProjectApplicationTest {

  protected MockMvc mockMvc;

  //?????? ????????? ?????? ??????
  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation)
            .snippets()
            .withDefaults(HttpDocumentation.httpRequest(), HttpDocumentation.httpResponse())
            .and()
            .templateEngine(createTemplateEngine())
        )
        .addFilter(new CharacterEncodingFilter("UTF-8",true))
        .build();
    System.setProperty("user.timezone", "UTC");
  }

  private static TemplateEngine createTemplateEngine() {
    return new MustacheTemplateEngine(
        new StandardTemplateResourceResolver(TemplateFormats.asciidoctor()),
        Mustache.compiler().escapeHTML(false), Map.of("tableCellContent", new AsciidoctorTableCellContentLambda()));
  }

  // ????????? ?????????
  public static RestDocumentationResultHandler document(String identifier,
      Snippet... snippets) {
    return MockMvcRestDocumentation.document(identifier,
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        snippets);
  }

  //path parameter ????????? format?????? ??????
  protected static Attributes.Attribute format(String value){
    return Attributes.key("format").value(value);
  }
}
