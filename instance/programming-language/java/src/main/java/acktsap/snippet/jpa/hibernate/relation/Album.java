package acktsap.snippet.jpa.hibernate.relation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*
  @DiscriminatorValue
    - 자손 table의 구분 컬럼 설정
    - 여기서는 "A"
 */
@Entity
@DiscriminatorValue("A")
public class Album extends Item {

    private String artist;
    private String etc;


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    @Override
    public String toString() {
        return "Album{" +
            "artist='" + artist + '\'' +
            ", etc='" + etc + '\'' +
            '}';
    }
}
