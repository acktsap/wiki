package acktsap.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Item {

  protected @Id @GeneratedValue Long id;

  @NonNull
  protected String name;

  @NonNull
  protected Integer value;

}
