package acktsap.relation;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Member extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "MEMBER_ID")
  private Long id;

  private String name;

  @Embedded
  private Address address;

  /*
    @OneToMany : 1:N을 Mapping 할 때 사용
      - mappedBy : 연관관계의 주인을 설정. 외래키가 있는 곳으로 설정. 1:N에서 항상 N쪽이 가짐
   */
  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();

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

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", address=" + address +
        '}';
  }
}