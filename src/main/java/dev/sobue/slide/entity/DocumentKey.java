package dev.sobue.slide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * Primary key object.
 *
 * @author SOBUE Sho
 */
@Data
@ToString
@Embeddable
public class DocumentKey implements Serializable {

  /**
   * Title name.
   */
  @Column(name = "TITLE", nullable = false)
  private String title;

  /**
   * Pager number.
   */
  @Column(name = "PAGE", nullable = false)
  private Integer page;
}
