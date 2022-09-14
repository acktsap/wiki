package acktsap

import acktsap.spring.lib.Library
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
open class SomeComponent(
    private val library: Library
) {
    @PostConstruct
    fun init() {
        println("library.someExtension: ${library.someExtension()}")
    }
}
