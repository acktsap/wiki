package acktsap.spring.lib

import org.junit.jupiter.api.Test

internal class LibraryExtensionsTest {

    @Test
    fun testLibraryExtension() {
        val library = Library()
        library.someExtension()
    }
}
