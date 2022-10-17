package acktsap.spring.lib

import org.junit.jupiter.api.Test

internal class KotlinOnlyLibraryTest {

    @Test
    fun testLibraryExtension() {
        val kotlinOnlyLibrary = KotlinOnlyLibrary()
        kotlinOnlyLibrary.helloWorld()
    }
}
