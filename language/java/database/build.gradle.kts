plugins {
    java
}

dependencies {
    /* jdbc */
    // simple jndi implementation for test purpose
    implementation("com.github.h-thurow:simple-jndi:0.23.0")

    /* jpa */
    // jpa api
    implementation("javax.persistence:javax.persistence-api:2.2")
    // jpa implementation
    implementation("org.hibernate:hibernate-core:6.0.0.Final")

    /* embedded db */
    implementation("com.h2database:h2:2.1.212")

    /* logging */
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.2.11")
}
