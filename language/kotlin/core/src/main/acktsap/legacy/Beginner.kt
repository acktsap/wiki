/*
 * Test.
 */

package legacy

object Obj {
    // public static final
    const val who = "world" // not assignment, just a declaration
}

// shift + shift general find
fun main(args: Array<String>) {
    // final, variable, can't be reassigned
    val who = "world" // not assignment, just a declaration

    // variable, can be reassigned
    var who2 = "world" // not assignment, just a declaration

    val name: String = "world"
    // type : derivced from bnf
    // extract type from common type
    // val name: String = "Hello, world"

    // newName: String? (nullable String)
    val newName = if (args.size > 0) {
        "Hello" // String
    } else {
        null // Nothing?
    }

    println("Hello, $who")
    println("Hello, $who2")
    println("Hello, $name")
    println("Hello, ${Obj.who}")
    println("Hello, $newName")
}

fun a(): Unit? {
    // without both, we get error
    // return Unit
    return null
}

fun b(): Nothing? {
    return null // alias of 'Nothing?' just an 'undefined one'
}

// no use (use keyword as a variable)
fun `should for null test`() {

}