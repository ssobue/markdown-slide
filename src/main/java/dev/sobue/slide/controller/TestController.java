package dev.sobue.slide.controller;

import java.io.InputStream;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

  private final Random random = new Random();

  @GetMapping("/test")
  public ResponseEntity<Void> test() throws Exception {
    InputStream is = getClass().getResourceAsStream("/application.yml");

    long cnt = 0L;
    do {
      if (random.nextInt() % 10 == 0) {
        Thread.sleep(1000L);
      }
    } while (cnt++ < 100);

    return ResponseEntity.ok().build();
  }
}
