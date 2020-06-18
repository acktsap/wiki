package acktsap.jpa.pattern.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Entity
@Builder(toBuilder = true)
public class EventHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "EVENT_ID")
  private Long eventId;

  @Column(name = "CHANGE_TYPE")
  private String changeType;

}
