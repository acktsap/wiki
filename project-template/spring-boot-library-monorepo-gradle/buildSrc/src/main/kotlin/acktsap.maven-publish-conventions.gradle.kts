plugins {
    `maven-publish`
}

version = "0.0.1-SNAPSHOT"
group = "acktsap"

publishing {
    publications {
        create<MavenPublication>("library") {
            from(components["java"])

            pom {
                name.set("spring-lib")
                description.set("Spring library test")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("acktsap")
                        name.set("Taeik Lim")
                        email.set("sibera21@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/acktsap/my-library.git")
                    developerConnection.set("scm:git:ssh://github.com/acktsap/my-library.git")
                    url.set("http://github.com/acktsap/my-library/")
                }
            }
        }
    }

    repositories {
        maven {
            credentials {
                // in '.envrc'
                // export OSS_USER=xxx
                // export OSS_PASSWORD=xxx
                username = System.getenv("OSS_USER")
                password = System.getenv("OSS_PASSWORD")
            }

            url = uri(
                if (!version.toString().endsWith("SNAPSHOT")) {
                    "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                } else {
                    "https://oss.sonatype.org/content/repositories/snapshots/"
                }
            )
        }
    }
}

tasks.create("install") {
    dependsOn("publishToMavenLocal")
}
