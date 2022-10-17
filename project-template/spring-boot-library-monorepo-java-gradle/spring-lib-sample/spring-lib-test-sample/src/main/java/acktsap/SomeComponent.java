package acktsap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acktsap.spring.lib.Library;

@Component
class SomeComponent {
    @Autowired
    private Library library;

    @PostConstruct
    void init() {
        System.out.println("library.someLibraryModuleMethod: " + library.someLibraryModuleMethod());
    }
}
