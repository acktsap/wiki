package acktsap.jpa.hibernate.relation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*

  Fetch
    - @OneToOne, @ManyToOne : default is FetchType.EAGER
    - @OneToMany, @ManyToMany : default is FetchType.LAZY
    - 권장되는 것은 모두 LAZY로 설정해 두는 것

  Cascade : 한개의 Entity를 영속 상태로 만들 때 연관된 Entity도 영속관계로 만들건지 설정
    - All
    - PERSIST
    - MERGE
    - REMOVE
    - REFRESH
    - DETACH

 */
@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    public enum OrderStatus {
        ORDER, CANCEL
    }

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    /*
      @ManyToOne : N:1관계를  Mapping 할 때 사용
        - optional : false로 하면 연관된 entity가 항상 있어야 함. default : true
        - fetch : Proxy같은거를 써서 나중에 loading할건지 (FetchType.LAZY) 바로 Loading할건지 (FetchType.EAGER)

      @JoinColumn : 외래키를 Mapping 할 때 사용
        - name : 왜래 키 이름 (기본값 : 필드명 + _ + 참조하는 테이블의 기본 키 컬럼명)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;      //주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /*
      @OneToOne : 1:1관계. 여기서는 얘가 DELIVERY_ID 에 대한 외래키를 관리함
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;  //배송정보

    private Date orderDate;     //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문상태


    public void setMember(Member member) {
        this.member = member;
        // 양방향 mapping을 해주는 것은 양방향 객체 그래프 탐색을 손쉽게 하기 위해서임
        // DB는 기본적으로 양방향이나 Object는 이렇게 단방향 2개를 설정해 줘야 양방향처럼 동작할 수 있음
        // Order가 연관관계의 주인이라서 DB에는 영향이 없지만 POJO 형태에서도 이를 표현할 수 있어야 함
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", orderDate=" + orderDate +
            ", status=" + status +
            '}';
    }
}
