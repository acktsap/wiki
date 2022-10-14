package acktsap.spring.application

import acktsap.someExtension
import org.junit.jupiter.api.Test

class LibraryExtensionsTest {
    @Test
    fun testLibraryExtension() {
        val library = Library()
        library.someExtension()
    }
}
