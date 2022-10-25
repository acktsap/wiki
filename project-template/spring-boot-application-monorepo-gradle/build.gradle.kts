plugins {
    id("org.jetbrains.kotlinx.kover")
}

// disable for root project
kover {
    isDisabled.set(true)
    engine.set(kotlinx.kover.api.DefaultIntellijEngine)
}

koverMerged {
    enable()

    filters {
        classes {
            excludes += listOf("com.example.subpackage.*")
        }
        annotations {
            excludes += listOf("com.example.Annotation", "*Generated")
        }
        projects {
            excludes += listOf()
        }
    }

    xmlReport {
        onCheck.set(true)
        reportFile.set(layout.buildDirectory.file("kover/result.xml"))
    }

    htmlReport {
        onCheck.set(true)
        reportDir.set(layout.buildDirectory.dir("kover/html"))
    }

    verify {
        onCheck.set(true)
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
                minValue = 50
                maxValue = 100
                counter = kotlinx.kover.api.CounterType.INSTRUCTION // (LINE, INSTRUCTION, BRANCH)
                valueType = kotlinx.kover.api.VerificationValueType.COVERED_PERCENTAGE // (COVERED_COUNT, MISSED_COUNT, COVERED_PERCENTAGE, MISSED_PERCENTAGE)
            }
        }
    }
}
