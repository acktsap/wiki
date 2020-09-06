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
    methodTest("Hello method!")
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

 TODO