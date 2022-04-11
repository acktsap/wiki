plugins {
    java
}

dependencies {
    /* jpa */
    implementation("org.hibernate:hibernate-core:5.6.3.Final")
    implementation("com.h2database:h2:2.0.202")

    /* logging */
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.2.11")
}
