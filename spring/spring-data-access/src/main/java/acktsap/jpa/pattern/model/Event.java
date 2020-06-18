package acktsap.jpa.pattern.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder(toBuilder = true)
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Builder.Default
  @Column(name = "STRING_VALUE")
  private String stringValue = "";

  @Builder.Default
  @Column(name = "INT_VALUE")
  private int intValue = 0;

}