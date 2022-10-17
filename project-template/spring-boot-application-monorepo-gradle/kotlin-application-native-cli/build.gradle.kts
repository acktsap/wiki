plugins {
    id("acktsap.kotlin-conventions")
    id("acktsap.coverage-conventions")
    id("acktsap.native-conventions")
}

dependencies {
}

graalvmNative {
    binaries {
        named("main") {
            // Main options
            mainClass.set("acktsap.application.KotlinNativeCliApplicationKt") // The main class to use, defaults to the application.mainClass
            debug.set(true) // Determines if debug info should be generated, defaults to false (alternatively add --debug-native to the CLI)
            verbose.set(true) // Add verbose output, defaults to false
            fallback.set(true) // Sets the fallback mode of native-image, defaults to false
            sharedLibrary.set(false) // Determines if image is a shared library, defaults to false if `java-library` plugin isn't included
            quickBuild.set(false) // Determines if image is being built in quick build mode (alternatively use GRAALVM_QUICK_BUILD environment variable, or add --native-quick-build to the CLI)
            richOutput.set(false) // Determines if native-image building should be done with rich output
        }
    }
}
