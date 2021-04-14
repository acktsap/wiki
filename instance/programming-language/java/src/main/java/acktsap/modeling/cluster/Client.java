package acktsap.modeling.cluster;

// request using connection
public interface Client {

    void setConnection(Connection connection);

    void request();

}
