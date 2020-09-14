/* 
    Reference

    https://docs.gradle.org/current/userguide/tutorial_using_tasks.html#learning-the-basics
*/




/**************************************
 * Basic
 **************************************/

/*
    Projects and tasks
        Every Gradle build is made up of one or more projects
        Each project is made up of one or more tasks
*/


/* 
    Hello world task
        ./gradlew hello
*/
tasks.register("hello") {
    doLast {
        println("Hello world!")
    }
}


/*
    Build scripts are code
        ./gradlew upper
*/
tasks.register("upper") {
    doLast {
        val someString = "mY_nAmE"
        println("Original: $someString")
        println("Upper case: ${someString.toUpperCase()}")

        repeat(4) { print("$it ") }
    }
}


/*
    Task dependencies
        ./gradlew taskX
*/
tasks.register("taskX") {
    // taskY is not defined yet. But it's possible because of lazy evaluation
    dependsOn("taskY") 
    doLast {
        println("taskX")
    }
}

tasks.register("taskY") {
    doLast {
        println("taskY")
    }
}


/*
    Dynamic tasks
        ./gradlew task0 or task1 or task2 or task3
*/
repeat(4) { counter ->
    tasks.register("task$counter") {
        doLast {
            println("I'm task number $counter")
        }
    }
}


/*
    Manipulating existing tasks
        ./gradlew task0
        ./gradlew hey
*/
tasks.named("task0") { dependsOn("task2", "task3") }

// calls doLast multiple times
val hey by tasks.registering {
    doLast {
        println("Hello Earth")
    }
}
hey {
    doFirst {
        println("Hello Venus")
    }
}
hey {
    doLast {
        println("Hello Mars")
    }
}
hey {
    doLast {
        println("Hello Jupiter")
    }
}


/*
    Extra task properties
        ./gradlew printProp
*/
tasks.register("myTask") {
    extra["myProperty"] = "myValue"
}

tasks.register("printProp") {
    doLast {
        println(tasks["myTask"].extra["myProperty"])
    }
}


/*
    Using methods
        ./gradlew method
*/
tasks.register("method") {
    doLast {
        methodTest("Hello method!")
    }
}

fun methodTest(message: String): Unit = println(message)


/*
    Default tasks
        ./gradlew
*/
defaultTasks("clean", "run")

task("clean") {
    doLast {
        println("Default Cleaning!")
    }
}

tasks.register("run") {
    doLast {
        println("Default Running!")
    }
}


/*
    Configuration phase hook
        ./gradlew distribution
        ./gradlew release
*/
tasks.register("distribution") {
    doLast {
        println("We build the zip with version=$version")
    }
}

tasks.register("release") {
    dependsOn("distribution")
    doLast {
        println("We release now")
    }
}

// hook
gradle.taskGraph.whenReady {
    // version must be read lazily
    version =
        if (hasTask(":release")) "1.0"
        else "1.0-SNAPSHOT"
}




/**************************************
 * Task Details
 **************************************/

/*
    Gradle tasks can has their own properties and method 

    Gradle tasks outcomes
        (no label) or EXECUTED
        UP-TO-DATE : By Incremental Builds
        FROM-CACHE : Outputs from a previous execution
        SKIPPED : eg. onlyIf returns false
        NO-SOURCE : eg. No *.java file to compile
*/


/*
    Defining tasks
        ./gradlew copy1 or copy2 or copy3
*/
tasks.register<Copy>("copy1") {
    from(file("srcDir"))
    into(buildDir)
    doLast {
        println("copy1")
    }
}

tasks {
    register<Copy>("copy2") {
        from(file("srcDir"))
        into(buildDir)
        doLast {
            println("copy2")
        }
    }
}

val copy3 by tasks.registering(Copy::class) {
    from(file("srcDir"))
    into(buildDir)
    doLast {
        println("copy3")
    }
}

/*
    Locating tasks
        ./gradlew access
        ./gradlew needCopy
*/
task("accessTarget")

tasks.register("access") {
    doLast {
        // using a DSL specific syntax
        val accessTarget by tasks.getting
        println("Access by dsl - ${accessTarget.name}")

        // via tasks collection
        println("Access by tasks collection1 - ${tasks["accessTarget"].name}")
        println("Access by tasks collection2 - ${tasks.named("accessTarget").get().name}")
        
        // by path
        println("Access by path1 - ${tasks.getByPath("accessTarget").path}")
        println("Access by path1 - ${tasks.getByPath(":accessTarget").path}")
    }
}

// by type
tasks.register("needCopy") {
    dependsOn(tasks.withType<Copy>())
}


/*
    Configuring tasks
        ./gradlew access
        ./gradlew needCopy
*/

tasks.register("myConfig")

// configure using Kotlin delegated properties and a lambda
val myConfig = tasks.named("myConfig") {
    doLast {
        println("My config1")
    }
}

myConfig {
    doLast {
        println("My config2")
    }
}


/*
    Passing arguments to a task constructor
        ./gradlew customTask
*/
open class CustomTask @javax.inject.Inject constructor(
    private val message: String,
    private val number: Int
) : DefaultTask()
// TODO: not work
// tasks.register<CustomTask>("customTask", "wow", 42) {
//    println("${it.message}")
// }


/*
    Adding dependencies to a task
        ./gradlew dependX
        ./gradlew dependLib
*/
// using task object
val dependX by tasks.registering {
    doLast {
        println("dependX")
    }
}
val dependY by tasks.registering {
    doLast {
        println("dependY")
    }
}
dependX {
    dependsOn(dependY)
}

// using a Gradle Provider
tasks.register("dependLib") {
    dependsOn(provider {
        tasks.filter { task -> task.name.startsWith("lib") }
    })
    doLast {
        println("dependLib")
    }
}
tasks.register("lib1") {
    doLast {
        println("lib1")
    }
}
tasks.register("lib2") {
    doLast {
        println("lib2")
    }
}
tasks.register("notALib") {
    // Always print when executing dependLib
    // since it's printed on configuration
    println("Always print when scaning")
    doLast {
        println("notALib")
    }
}


/*
    Ordering tasks
        TODO
*/