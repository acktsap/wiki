package acktsap.pattern.dbread;

public class Customer {
    private final long id;

    private final String name;

    public Customer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
