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