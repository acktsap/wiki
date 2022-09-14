plugins {
    java
}

dependencies {
    /* utils */
    // guava
    implementation("com.google.guava:guava:31.1-jre")

    /* cache */
    // caffeine
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")
    implementation("com.github.ben-manes.caffeine:guava:3.1.1") // extension
    implementation("com.github.ben-manes.caffeine:jcache:3.1.1") // extension

    /* logging */
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    /* reactive */
    // reactor
    implementation("io.projectreactor:reactor-core:3.4.21")

    /* markdown */
    // commonmark
    implementation("org.commonmark:commonmark:0.19.0")
}
