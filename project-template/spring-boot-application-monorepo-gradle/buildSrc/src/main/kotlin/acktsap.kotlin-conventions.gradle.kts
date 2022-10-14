import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlinx.kover")
    id("org.jlleitschuh.gradle.ktlint")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict") // enable jsr305 null-safety in kotlin
        jvmTarget = "17"
    }
}

kover {
    isDisabled.set(false) // true to disable instrumentation and all Kover tasks in this project
    engine.set(kotlinx.kover.api.DefaultJacocoEngine) // to change engine, use kotlinx.kover.api.IntellijEngine("xxx") or kotlinx.kover.api.JacocoEngine("xxx")
    filters { // common filters for all default Kover tasks
        classes { // common class filter for all default Kover tasks in this project
            includes += "com.example.*" // class inclusion rules
            excludes += listOf("com.example.subpackage.*") // class exclusion rules
        }
        annotations { // common annotation filter for all default Kover tasks in this project
            excludes += listOf(
                "com.example.Annotation",
                "*Generated"
            ) // exclude declarations marked by specified annotations
        }
    }

    instrumentation {
        excludeTasks += "dummy-tests" // set of test tasks names to exclude from instrumentation. The results of their execution will not be presented in the report
    }

    xmlReport {
        onCheck.set(false)
    }

    htmlReport {
        onCheck.set(true) // set to true to run koverHtmlReport task during the execution of the check task (if it exists) of the current project
        reportDir.set(layout.buildDirectory.dir("jacocoHtml")) // change report directory
        overrideFilters {
            classes { // override common class filter
                includes += "com.example2.*" // class inclusion rules
                excludes += listOf("com.example2.subpackage.*") // override class exclusion rules
            }
            annotations { // override common annotation filter for HTML report (filtering will take place only by the annotations specified here)
                excludes += listOf("com.example2.Annotation")
            }
        }
    }

    verify {
        onCheck.set(true) // set to true to run koverVerify task during the execution of the check task (if it exists) of the current project
        rule { // add verification rule
            isEnabled = true // set to false to disable rule checking
            name = null // custom name for the rule
            target =
                kotlinx.kover.api.VerificationTarget.ALL // specify by which entity the code for separate coverage evaluation will be grouped

            overrideClassFilter { // override common class filter
                includes += "acktsap.*" // override class inclusion rules
                excludes += listOf("acktsap.test.*") // override class exclusion rules
            }
            overrideAnnotationFilter { // override common annotation filter (filtering will take place only by the annotations specified here)
                excludes += "*verify.*Generated" // declarations marked only by these annotations will be excluded from this rule
            }

            bound { // add rule bound
                minValue = 10
                maxValue = 20
                counter =
                    kotlinx.kover.api.CounterType.LINE // change coverage metric to evaluate (LINE, INSTRUCTION, BRANCH)
                valueType =
                    kotlinx.kover.api.VerificationValueType.COVERED_PERCENTAGE // change counter value (COVERED_COUNT, MISSED_COUNT, COVERED_PERCENTAGE, MISSED_PERCENTAGE)
            }
        }
    }
}
