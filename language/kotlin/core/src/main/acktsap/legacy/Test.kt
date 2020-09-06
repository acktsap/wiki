package legacy

data class Student2(val name: String) {
  var score: Int = 0
}

fun Student2(name: String, score: Int): Student2 {
  return Student2(name).apply { this.score = score }
}

// tips
// no inline function
// no val
// no library for java

fun main() {
  // what's the difference?
  // is it fine?
  Student2("")
  Student2("", 0)
}


