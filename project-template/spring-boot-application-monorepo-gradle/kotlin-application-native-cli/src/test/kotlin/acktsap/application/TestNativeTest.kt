package acktsap.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TestNativeTest {

    @Test
    fun test() {
        val testNative = TestNative(324)
        assertThat(testNative.value)
    }
}
