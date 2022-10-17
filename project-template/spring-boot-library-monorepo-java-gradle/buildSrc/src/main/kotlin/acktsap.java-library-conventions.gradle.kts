plugins {
    `java-library`
    jacoco
    checkstyle
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    withSourcesJar() // generate source jar
    withJavadocJar() // generate javadoc file
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
        configFile = file("${project.rootDir}/buildSrc/config/checkstyle.xml")
        configProperties = mapOf(
            "suppressionFile" to file("${project.rootDir}/buildSrc/config/checkstyle-suppressions.xml")
        )
        xml.required.set(false)
        html.required.set(true)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    maxParallelForks = Runtime.getRuntime().availableProcessors() // run test in parallel

    finalizedBy(
        tasks.jacocoTestReport, // report is always generate
        tasks.jacocoTestCoverageVerification, // fail if violate rules
    )

    configure<JacocoTaskExtension> {
        // https://docs.gradle.org/current/userguide/jacoco_plugin.html#default_values_of_the_jacoco_task_extension
        isEnabled = true
        setDestinationFile(layout.buildDirectory.file("jacoco/${name}.exec").get().asFile)
        includes = listOf()
        excludes = listOf()
    }
}

jacoco {
    toolVersion = "0.8.8"
    reportsDirectory.set(layout.buildDirectory.dir("jacoco/report"))
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "1.00".toBigDecimal() // at least 100%
            }
            includes = listOf()
            excludes = listOf()
        }
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.named<Test>("test")) // tests are required to run before generating the report

    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.required.set(true)
    }
}
