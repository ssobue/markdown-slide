package dev.sobue.slide.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity(name = "DOCUMENT")
public final class MarkdownDocument {

  @EmbeddedId
  private DocumentKey documentKey;

  @Column(name = "BODY")
  private String body;
}
