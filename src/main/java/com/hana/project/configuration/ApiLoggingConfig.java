package com.hana.project.configuration;

import java.util.Collection;
import java.util.LinkedHashSet;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Configuration
public class ApiLoggingConfig {

  private final Collection<RequestMatcher> ignoreRequestMatcher = new LinkedHashSet<>();

  public ApiLoggingConfig() {
    ignoreRequestMatcher.add(new AntPathRequestMatcher("/docs/**"));
  }

  @Bean
  public AbstractRequestLoggingFilter loggingFilter() {
    AbstractRequestLoggingFilter loggingFilter = new AbstractRequestLoggingFilter() {

      @Override
      protected boolean shouldLog(HttpServletRequest request) {
        return logger.isDebugEnabled() && !isLoggingIgnored(request);
      }

      @Override
      protected void beforeRequest(HttpServletRequest request, String message) {
        // Nothing to do
      }

      @Override
      protected void afterRequest(HttpServletRequest request, String message) {
        logger.debug(message);
      }
    };
    loggingFilter.setIncludeHeaders(true);
    loggingFilter.setIncludePayload(true);
    loggingFilter.setIncludeQueryString(true);
    loggingFilter.setIncludeClientInfo(true);

    return loggingFilter;
  }


  private boolean isLoggingIgnored(HttpServletRequest req) {
    return ignoreRequestMatcher.stream()
        .anyMatch(requestMatcher -> requestMatcher.matches(req));
  }
}
