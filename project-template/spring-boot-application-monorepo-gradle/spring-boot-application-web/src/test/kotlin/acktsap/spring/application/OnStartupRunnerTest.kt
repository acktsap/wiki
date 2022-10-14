package acktsap.spring.application

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class OnStartupRunnerTest {
    @Autowired
    lateinit var sut: OnStartupRunner

    @Test
    fun test() {
        sut.run(mock())
    }
}
