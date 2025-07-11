package dev.sobue.slide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.ToString;

/**
 * Markdown documents.
 *
 * @author SOBUE Sho
 */
@Data
@ToString
@Entity(name = "DOCUMENT")
public final class MarkdownDocument {

  /**
   * primary key object.
   */
  @EmbeddedId
  private DocumentKey documentKey;

  /**
   * Document body.
   */
  @Column(name = "BODY", nullable = false)
  @Lob
  private String body;
}
