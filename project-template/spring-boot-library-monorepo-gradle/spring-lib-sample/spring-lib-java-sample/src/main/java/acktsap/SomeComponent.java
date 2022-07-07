package acktsap;

import acktsap.spring.lib.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class SomeComponent {
    @Autowired
    private Library library;

    @PostConstruct
    void init() {
        System.out.println("library.someLibraryModuleMethod: " + library.someLibraryModuleMethod());
    }
}
