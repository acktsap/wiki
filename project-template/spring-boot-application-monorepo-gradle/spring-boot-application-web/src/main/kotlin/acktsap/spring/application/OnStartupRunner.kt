package acktsap.spring.application

import acktsap.spring.application.module.JavaModel
import acktsap.spring.application.module.KotlinModel
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class OnStartupRunner : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val javaModel = JavaModel(3)
        val kotlinModel = KotlinModel(30)
        println("JavaModel value: ${javaModel.value}")
        println("KotlinModel value: ${kotlinModel.value}")
    }
}
