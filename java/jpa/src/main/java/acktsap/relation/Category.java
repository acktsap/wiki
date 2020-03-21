package acktsap.relation;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Category {

  @Id
  @GeneratedValue
  @Column(name = "CATEGORY_ID")
  private Long id;

  private String name;

  /*
    @ManyToMany
      - N:N에 대해 처리, Category:Item이 N:N이라 db에선느 CATEGORY_ITEM이 있지만
        Object에서는 서로 참조만 가지고 있으면 되기 때문에 이렇게 사용할 수 있음
      - 여기는 Category를 연관관계의 주인으로 설정해서
        @JoinTable을 사용
      - But 연결 테이블에 대한 객체를 직접 생성해서 사용하는 것을 권장

    @JoinTable
      - name : 연결 테이블 이름
      - joinColumns : 현재 방향과 매핑할 Join Column, 여기서는 CATEGORY_ID
      - joinColumns : 반대 방향과 매핑할 Join Column, 여기서는 ITEM_ID
   */
  @ManyToMany
  @JoinTable(name = "CATEGORY_ITEM",
      joinColumns = @JoinColumn(name = "CATEGORY_ID"),
      inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
  private List<Item> items = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PARENT_ID")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> child = new ArrayList<Category>();

  //==연관관계 메서드==//
  public void addChildCategory(Category child) {
    this.child.add(child);
    child.setParent(this);
  }

  public void addItem(Item item) {
    items.add(item);
  }


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

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public Category getParent() {
    return parent;
  }

  public void setParent(Category parent) {
    this.parent = parent;
  }

  public List<Category> getChild() {
    return child;
  }

  public void setChild(List<Category> child) {
    this.child = child;
  }

  @Override
  public String toString() {
    return "Category{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}