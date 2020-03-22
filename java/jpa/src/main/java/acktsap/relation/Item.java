package acktsap.relation;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

/*
  @Inheritance : 상속 mapping을 지정 여기서는 Single Table 전략 사용
    - SINGLE_TABLE : A single table per class hierarchy. 구준 컬럼 필요
    - TABLE_PER_CLASS : A table per concrete entity class.
    - JOINED : subtable과 supertable를 join해서 subclass에 값을 할당

  @DiscriminatorColumn
    - 부모 table 에 구분 컬럼을 지정. 여기서는 DTYPE

  create table Item (
     DTYPE varchar(31) not null,
      ITEM_ID bigint not null,
      name varchar(255),
      price integer not null,
      stockQuantity integer not null,
      artist varchar(255),
      etc varchar(255),
      author varchar(255),
      isbn varchar(255),
      actor varchar(255),
      director varchar(255),
      primary key (ITEM_ID)
  )
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;

  private String name;        //이름
  private int price;          //가격
  private int stockQuantity;  //재고수량

  /*
    @ManyToMany
      - N:N에 대해 처리, Category:Item이 N:N이라 db에선느 CATEGORY_ITEM이 있지만
        Object에서는 서로 참조만 가지고 있으면 되기 때문에 이렇게 사용할 수 있음
      - 여기는 Category를 연관관계의 주인으로 설정해서
        mappedBy를 사용
   */
  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<Category>();

  //Getter, Setter
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  @Override
  public String toString() {
    return "Item{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        '}';
  }
}