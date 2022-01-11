package com.hana.project.configuration;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageConfiguration {

  @Bean
  public PageableHandlerMethodArgumentResolverCustomizer customize() {
    return p -> {
      p.setOneIndexedParameters(true);
      p.setPageParameterName("pageNo");
      p.setSizeParameterName("pageSize");
    };
  }

  @JsonComponent
  static class PageImplJacksonSerializer extends JsonSerializer<PageImpl<?>> {

    @Override
    public void serialize(PageImpl<?> page, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeStartObject();
      gen.writeObjectField("content", page.getContent());
      gen.writeNumberField("totalPage", page.getTotalPages());
      gen.writeNumberField("totalCount", page.getTotalElements());
      gen.writeNumberField("pageSize", page.getSize());
      gen.writeNumberField("pageNo", page.getNumber() + 1);
      gen.writeEndObject();
    }
  }
}
