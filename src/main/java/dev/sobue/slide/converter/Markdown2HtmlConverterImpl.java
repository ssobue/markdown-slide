package dev.sobue.slide.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class Markdown2HtmlConverterImpl implements Markdown2HtmlConverter {

  private static final String API_ENDPOINT = "https://api.github.com/markdown";

  @Override
  public String convert2html(@NonNull final String markdown) {
    final CountDownLatch latch = new CountDownLatch(1);
    final StringBuilder builder = new StringBuilder();

    final Map<String, String> body = new HashMap<>();
    body.put("text", markdown);

    try {
      WebClient.create()
          .post()
          .uri(API_ENDPOINT)
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(body)
          .retrieve()
          .toEntity(String.class)
          .subscribe(response -> {
            builder.append(response.getBody());
            latch.countDown();
          });
      latch.await();
    } catch (InterruptedException exception) {
      throw new IllegalArgumentException(exception);
    }

    return builder.toString();
  }
}
