package acktsap.jpa.pattern.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  Long id;

  @Column(name = "STRING_VALUE")
  String stringValue = "";

  @Column(name = "INT_VALUE")
  int intValue = 0;

}