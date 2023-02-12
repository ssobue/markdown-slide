package dev.sobue.slide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
