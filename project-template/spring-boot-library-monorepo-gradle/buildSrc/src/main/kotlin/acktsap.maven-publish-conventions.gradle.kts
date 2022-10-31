plugins {
    `maven-publish`
    signing
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set(project.name)
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
                    url.set("https://github.com/acktsap/my-library/")
                }
            }
        }
    }

    repositories {
        maven {
            credentials {
                // in '.envrc'
                // export MAVEN_USER=xxx
                // export MAVEN_PASSWORD=xxx
                username = System.getenv("MAVEN_USER")
                password = System.getenv("MAVEN_PASSWORD")
            }

            url = uri(
                // in '.envrc'
                // export REPO_URL=https://some.url
                // export REPO_SNAPSHOT_URL=https://some.url
                if (!version.toString().endsWith("SNAPSHOT")) {
                    System.getenv("REPO_URL") ?: "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                } else {
                    System.getenv("REPO_SNAPSHOT_URL") ?: "https://oss.sonatype.org/content/repositories/snapshots/"
                }
            )
        }
    }
}

signing {
    // signing.keyId, signing.password, signing.secretKeyRingFile in ~/.gradle.properties
    // ~/.gradle/gradle.properties
    // see also: https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials
    sign(publishing.publications["maven"])
}

tasks.create("install") {
    dependsOn("publishToMavenLocal")
}

tasks.withType<PublishToMavenRepository>() {
    doFirst {
        println("publishing to ${repository.url}")
    }
}
