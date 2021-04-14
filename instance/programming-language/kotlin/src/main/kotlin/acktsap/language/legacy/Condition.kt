package legacy

interface MyInterface

class MyClass1 : MyInterface

class MyClass2 : MyInterface

object MyObject : MyInterface

enum class Color {
    RED, BLUE, YELLOW
}

// when::or
fun userAnswer(userInput: String): Boolean = when (userInput.toLowerCase()) {
    "yes", "y" -> true
    "no", "n" -> false
    else -> TODO()
}

// when::and
fun isVip(group: String, level: Int): Boolean {
    return when (setOf(group, level)) {
        setOf("platinum", 1) -> true
        else -> false
    }
}

// when::local variable
fun userAnswer2(userInput: String): Boolean = when (val lower = userInput.toLowerCase()) {
    "yes", "y" -> true
    "no", "n" -> false
    else -> lower.toBoolean()
}

fun main(m: MyInterface): String {
    // if else

    val condition = true
    val thenClause = {}
    val elseClause = {}

    var xx: Unit = if (condition) {
        thenClause()
    } else {
        elseClause()
    }

    // throw error (no return)
    // var xx2 = if (condition) {
    //     thenClause()
    // }

    var xx2 = if (condition) {
        thenClause()
    } else {
        // recommanded (완전함수를 위해, if not, 부분함수 which triggers bug)
        null
    }

    // when
    // cons
    // 1. conflicts with mockito when
    // 2. conflicts with mockito when

    val a: Any = 0
    // convert to java switch statement
    when (a) {
        is String -> println("")
        "10" -> println("string 10") // not runs (blocked by upper 'is String')
        10 -> println("10")
        else -> println("none")
    }

    return when (m) {
        is MyClass1 -> "1"
        // must be present
        else -> "none"
    }

    val m2 = Color.RED
    return when (m2) {
        Color.RED -> "red"
        Color.BLUE -> "blue"
        Color.YELLOW -> "yellow"
        // no else statement
        // if necessary (throws exception
        // else -> TODO()
    }
}
