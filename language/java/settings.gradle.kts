rootProject.name = "java"

// core
include(":base")
include(":database")
include(":monitoring")
include(":security")
include(":testing")

// spring
include(":spring:spring-batch")
include(":spring:spring-core")
include(":spring:spring-data-jdbc")
include(":spring:spring-testing")
include(":spring:spring-reactive")
include(":spring:spring-cloud-sleuth")
