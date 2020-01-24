package acktsap.sample.mvchelloworld;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class Event {

  protected String name;

  protected int limitOfEnrollment;

  protected LocalDateTime startDateTime;

  protected LocalDateTime endDateTime;

}
