rootProject.name = "java"

// core
include(":base")
include(":data-access")
include(":monitoring")
include(":security")
include(":testing")

// spring
include(":spring:spring-core")
include(":spring:spring-legacy-servlet")
include(":spring:spring-web-servlet")
include(":spring:spring-data-access")
include(":spring:spring-kafka")
include(":spring:spring-testing")
include(":spring:spring-reactive")
include(":spring:spring-batch")
include(":spring:spring-boot")
include(":spring:spring-cloud-sleuth")
