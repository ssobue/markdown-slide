package dev.sobue.slide.converter;

import java.net.URI;
import java.util.HashMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Markdown2HtmlConverterImpl implements Markdown2HtmlConverter {

  private static final String API_ENDPOINT = "https://api.github.com/markdown";

  private final RestTemplateBuilder builder;

  @Override
  public String convert2html(@NonNull final String markdown) {
    var body = new HashMap<String, String>();
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
