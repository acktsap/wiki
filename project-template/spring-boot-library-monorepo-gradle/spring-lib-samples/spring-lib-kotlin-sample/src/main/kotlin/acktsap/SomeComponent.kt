package acktsap

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
