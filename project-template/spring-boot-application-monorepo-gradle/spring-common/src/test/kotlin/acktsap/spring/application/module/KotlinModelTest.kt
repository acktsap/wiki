package acktsap.spring.application.module

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinModelTest {

    @Test
    fun test() {
        val kotlinModel = KotlinModel(3)
        assertThat(kotlinModel.value).isEqualTo(3)
    }
}
