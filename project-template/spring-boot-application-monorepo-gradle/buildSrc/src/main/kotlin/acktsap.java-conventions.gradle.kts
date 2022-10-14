plugins {
    `java-library`
    jacoco
    checkstyle
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.jar {
    // customizing manifest file
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        configFile = file("${project.rootDir}/src/checkstyle/checkstyle.xml")
        configProperties = mapOf(
            "suppressionFile" to file("${project.rootDir}/src/checkstyle/checkstyle-suppressions.xml")
        )
        xml.required.set(false)
        html.required.set(true)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    maxParallelForks = Runtime.getRuntime().availableProcessors()
    finalizedBy(tasks.jacocoTestReport) // report is always generate
}

tasks.jacocoTestReport {
    dependsOn(tasks.named<Test>("test")) // tests are required to run before generating the report

    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}
