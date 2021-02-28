package acktsap.jpa.hibernate.relation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*
  @DiscriminatorValue
    - 자손 table의 구분 컬럼 설정
    - 여기서는 "B"
 */
@Entity
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String isbn;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{}";
    }
}
