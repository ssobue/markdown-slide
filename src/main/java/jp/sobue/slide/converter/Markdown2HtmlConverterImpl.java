package jp.sobue.slide.converter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Markdown2HtmlConverterImpl implements Markdown2HtmlConverter {

  private static final String API_ENDPOINT = "https://api.github.com/markdown";

  private final RestTemplateBuilder builder;

  @Autowired
  public Markdown2HtmlConverterImpl(RestTemplateBuilder builder) {
    this.builder = builder;
  }

  @Override
  public String convert2html(String markdown) {
    Map<String, String> body = new HashMap<>();
    body.put("text", markdown);

    ResponseEntity<String> responseEntity =
        builder
            .build()
            .exchange(
                RequestEntity.post(URI.create(API_ENDPOINT))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body),
                String.class);

    return responseEntity.getBody();
  }
}
