plugins {
    java
}

dependencies {
    /* jpa */
    // h2
    implementation("org.hibernate:hibernate-core:5.6.3.Final")
    implementation("com.h2database:h2:2.0.202")

    /* utils */
    // guava
    implementation("com.google.guava:guava:31.0.1-jre")

    /* cache */
    // caffeine
    implementation("com.github.ben-manes.caffeine:caffeine:3.0.5")
    implementation("com.github.ben-manes.caffeine:guava:3.0.5") // extension
    implementation("com.github.ben-manes.caffeine:jcache:3.0.5") // extension

    /* logging */
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("ch.qos.logback:logback-classic:1.2.9")

    /* reactive */
    // reactor
    implementation("io.projectreactor:reactor-core:3.4.16")
}
