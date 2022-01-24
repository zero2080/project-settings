package com.hana.project.configuration;

import com.hana.project.util.NcloudConfigurationProperties;
import com.hana.project.util.RestApiComponent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

  @Bean
  public RestApiComponent restApiComponent() {
    return new RestApiComponent();
  }

  @Bean
  @ConfigurationProperties(prefix = "ncloud")
  public NcloudConfigurationProperties ncloudConfigurationProperties() {
    return new NcloudConfigurationProperties();
  }
}
