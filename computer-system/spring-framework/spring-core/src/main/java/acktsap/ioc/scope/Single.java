package acktsap.ioc.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // default scope : SingleTon
public class Single {

    @Autowired
    ObjectFactory<Proto> proto;

    public Proto getProto() {
        return proto.getObject();
    }
}