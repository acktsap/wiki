plugins {
    id("org.jetbrains.kotlinx.kover") // kover also covers java code
}

kover {
    // https://github.com/Kotlin/kotlinx-kover#configuring-project
    isDisabled.set(false) // true to disable instrumentation and all Kover tasks in this project
    engine.set(kotlinx.kover.api.DefaultIntellijEngine) // use IntellijEngine to cover all kotlin

    xmlReport {
        onCheck.set(true)
        reportFile.set(layout.buildDirectory.file("kover/result.xml"))
    }

    htmlReport {
        onCheck.set(true)
        reportDir.set(layout.buildDirectory.dir("kover/html"))
        overrideFilters {
            classes { // override common class filter
                excludes += listOf("com.example2.subpackage.*") // override class exclusion rules
            }
            annotations { // override common annotation filter for HTML report (filtering will take place only by the annotations specified here)
                excludes += listOf("com.example2.Annotation")
            }
        }
    }

    verify {
        onCheck.set(false) // enable to verify at subproject level
        rule {
            isEnabled = true
            name = "Test Coverage Rule" // custom name for the rule
            target = kotlinx.kover.api.VerificationTarget.ALL // specify by which entity the code for separate coverage evaluation will be grouped

            overrideClassFilter { // override common class filter
                excludes += listOf("com.example.verify.subpackage.*") // override class exclusion rules
            }
            overrideAnnotationFilter { // override common annotation filter (filtering will take place only by the annotations specified here)
                excludes += "*verify.*Generated" // declarations marked only by these annotations will be excluded from this rule
            }

            bound { // add rule bound
                minValue = 90
                maxValue = 100
                counter = kotlinx.kover.api.CounterType.INSTRUCTION // (LINE, INSTRUCTION, BRANCH)
                valueType = kotlinx.kover.api.VerificationValueType.COVERED_PERCENTAGE // (COVERED_COUNT, MISSED_COUNT, COVERED_PERCENTAGE, MISSED_PERCENTAGE)
            }
        }
    }
}
