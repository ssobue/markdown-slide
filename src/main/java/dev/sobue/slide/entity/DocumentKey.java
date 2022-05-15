package dev.sobue.slide.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Embeddable
public class DocumentKey implements Serializable  {

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "PAGE", nullable = false)
  private Integer page;
}
