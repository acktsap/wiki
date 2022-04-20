plugins {
    java
}

dependencies {
    /* jpa */
    implementation("org.hibernate:hibernate-core:6.0.0.Final")

    /* embedded db */
    implementation("com.h2database:h2:2.1.210")

    /* logging */
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.2.11")
}
