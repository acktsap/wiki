package acktsap.relation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*
  @DiscriminatorValue
    - 자손 table의 구분 컬럼 설정
    - 여기서는 "M"
 */
@Entity
@DiscriminatorValue("M")
public class Movie extends Item {

  private String director;
  private String actor;

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getActor() {
    return actor;
  }

  public void setActor(String actor) {
    this.actor = actor;
  }

  @Override
  public String toString() {
    return "Movie{" +
        "director='" + director + '\'' +
        ", actor='" + actor + '\'' +
        '}';
  }
}