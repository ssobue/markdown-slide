package dev.sobue.slide.converter;

import java.net.URI;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static java.util.Collections.singletonMap;

/**
 * Implementation for Converter: HTML from Markdown.
 *
 * @author SOBUE Sho
 */
@Component
public class Markdown2HtmlConverterImpl implements Markdown2HtmlConverter {

  /**
   * REST Client.
   */
  private final RestClient restClient;

  /**
   * GitHub API endpoint.
   */
  private final URI apiEndPoint;

  /**
   * Constructor.
   *
   * @param builder Client builder.
   * @param apiEndPoint API endpoint.
   */
  public Markdown2HtmlConverterImpl(
      RestClient.Builder builder,
      @Value("${github.api.markdown:https://api.github.com/markdown}") String apiEndPoint) {
    this.restClient = builder.build();
    this.apiEndPoint = URI.create(apiEndPoint);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String convert2html(@NonNull final String markdown) {
    return restClient
        .post()
        .uri(apiEndPoint)
        .contentType(MediaType.APPLICATION_JSON)
        .body(singletonMap("text", markdown))
        .retrieve()
        .body(String.class);
  }
}
