plugins {
    id("org.graalvm.buildtools.native")
}

graalvmNative {
    // search GRAALVM_HOME and JAVA_HOME in that order to search toolchain
    toolchainDetection.set(false)
}
