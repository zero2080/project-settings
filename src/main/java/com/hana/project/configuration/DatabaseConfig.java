package com.hana.project.configuration;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DatabaseConfig {

  @Bean
  @Profile("local")
  @ConfigurationProperties("spring.datasource.hikari")
  public DataSource datasource() throws SQLException {
    Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
    return new HikariDataSource();
  }
}