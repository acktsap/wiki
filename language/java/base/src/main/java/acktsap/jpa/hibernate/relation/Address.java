package acktsap.jpa.hibernate.relation;

import javax.persistence.Embeddable;

/*
  @Embeddable : JPA에서 값 타입을 직접 정의해서 사용할 수 있게 해줌

  값 타입을 정의할 때는 equals, hashCode, toString을 정의하는 것을 잊지 말자!
  (원래 Java model을 정의할 때도 그렇지만..)
 */
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Address{" +
            "city='" + city + '\'' +
            ", street='" + street + '\'' +
            ", zipcode='" + zipcode + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) {
            return false;
        }
        if (street != null ? !street.equals(address.street) : address.street != null) {
            return false;
        }
        if (zipcode != null ? !zipcode.equals(address.zipcode) : address.zipcode != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        return result;
    }
}
