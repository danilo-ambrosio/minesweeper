package com.deviget.minesweeper.infra;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${endpoints.cors.allowed-origins}")
  private String allowedOrigins;

  @Override
  public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new UserIdArgumentResolver());
  }

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins(allowedOrigins)
            .allowedMethods(HttpMethod.POST.name(), HttpMethod.PATCH.name(), HttpMethod.GET.name());
  }

  @Bean
  public Gson gson() {
    return new Gson();
  }

}
