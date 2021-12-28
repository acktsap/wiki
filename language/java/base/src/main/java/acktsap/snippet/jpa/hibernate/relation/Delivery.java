package acktsap.snippet.jpa.hibernate.relation;

import javax.persistence.*;

@Entity
public class Delivery {

    public enum DeliveryStatus {
        READY, COMP
    }

    @Id
    @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    /*
      @OneToOne : 1:1관계
        - mappedBy : 연관관계의 주인을 설정. 외래키가 있는 곳으로 설정. 1:1에서는 많이 사용하는 쪽으로 설정
     */
    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]

    public Delivery() {
    }

    public Delivery(Address address) {
        this.address = address;
        this.status = DeliveryStatus.READY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
            "id=" + id +
            ", address=" + address +
            ", status=" + status +
            '}';
    }
}
