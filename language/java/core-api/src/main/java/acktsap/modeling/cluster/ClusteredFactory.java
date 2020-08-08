package acktsap.modeling.cluster;

public interface ClusteredFactory<ClientT> {

  ClientT create(Connection... connections);

}
