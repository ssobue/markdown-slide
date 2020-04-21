package jp.sobue.slide.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public final class MarkdownDocument {

  private String title;

  private String body;
}
