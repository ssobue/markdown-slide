package dev.sobue.slide.converter;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Markdown2HtmlConverterImplTests {

  @Test
  void convert2htmlPostsMarkdownToConfiguredEndpoint() throws IOException {
    var requestBody = new AtomicReference<String>();
    var contentType = new AtomicReference<String>();
    var server = HttpServer.create(new InetSocketAddress(0), 0);
    server.createContext("/markdown", exchange -> {
      requestBody.set(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
      contentType.set(exchange.getRequestHeaders().getFirst("Content-Type"));
      var responseBody = "<p>converted</p>".getBytes(StandardCharsets.UTF_8);
      exchange.sendResponseHeaders(200, responseBody.length);
      exchange.getResponseBody().write(responseBody);
      exchange.close();
    });
    server.start();

    try {
      var endpoint = "http://localhost:" + server.getAddress().getPort() + "/markdown";
      var converter = new Markdown2HtmlConverterImpl(RestClient.builder(), endpoint);

      var actual = converter.convert2html("# title");

      assertEquals("<p>converted</p>", actual);
      assertTrue(requestBody.get().contains("# title"));
      assertTrue(contentType.get().startsWith("application/json"));
    } finally {
      server.stop(0);
    }
  }
}
