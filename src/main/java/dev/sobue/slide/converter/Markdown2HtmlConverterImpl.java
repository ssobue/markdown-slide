package dev.sobue.slide.converter;

import java.net.URI;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import static java.util.Collections.singletonMap;
import static org.springframework.http.RequestEntity.post;

@Component
public class Markdown2HtmlConverterImpl implements Markdown2HtmlConverter {

  private final RestOperations restOperations;

  private final URI apiEndPoint;

  public Markdown2HtmlConverterImpl(
      RestTemplateBuilder builder,
      @Value("${github.api.markdown:https://api.github.com/markdown}") String apiEndPoint) {
    this.restOperations = builder.build();
    this.apiEndPoint = URI.create(apiEndPoint);
  }

  @Override
  public String convert2html(@NonNull final String markdown) {
    return restOperations
        .exchange(
            post(apiEndPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(singletonMap("text", markdown)),
            String.class)
        .getBody();
  }
}
