rootProject.name = "spring-boot-library-monorepo-gradle"

include("spring-boot-autoconfigure-lib")
include("spring-boot-starter-lib")
include("spring-lib")
include("spring-lib-module")

include("spring-lib-samples:spring-lib-java-sample")
include("spring-lib-samples:spring-lib-kotlin-sample")
