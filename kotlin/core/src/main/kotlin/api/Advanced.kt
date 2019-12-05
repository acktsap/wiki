package api

class A {
  // this:0, human: 1, getJob: 2
  fun coreEngine(human: Human, getJob2: (Human) -> String): String {
    // Human is receiver
    val getJob = getJob2 as Human.() -> String
    return human.getJob()
  }

  fun run(human: Human): String {
    return when (human) {
      is Police -> coreEngine(human, getJob3)
      is Criminal -> coreEngine(human, ::getJob2)
      else -> ""
    }
  }

  // not work
//    fun Human.getJob3(): String {
//        return ""
//    }

  fun getJob2(human: Human): String {
    return "police"
  }

  val getJob3: (Human) -> String = { human: Human ->
    "police"
  }
}

fun main() {
  val human: Human = Police()
}