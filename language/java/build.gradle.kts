subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            name = "spring.milestone"
            url = uri("https://repo.spring.io/milestone")
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
}
