package mastermind

import java.lang.Integer.min


data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

// O(n)
fun evaluateGuess1(secret: String, guess: String): Evaluation {
  var rightPosition = 0
  var wrongPosition = 0

  val secretTarget = IntArray(6) { 0 }
  val guessTarget = IntArray(6) { 0 }

  val indexOf: (Char) -> Int = { c -> c.toInt() - 'A'.toInt() }
  val hasTarget: (Int, IntArray) -> Boolean = { i, storage -> 0 < storage[i] }
  val consume: (Int, IntArray) -> Unit = { i, storage ->
    --storage[i]
    ++wrongPosition
  }
  val store: (Int, IntArray) -> Unit = { i, storage -> ++storage[i] }

  guess.forEachIndexed { i, g ->
    when (g) {
      secret[i] -> ++rightPosition
      else -> {
        val sIndex = indexOf(secret[i])
        val gIndex = indexOf(g)
        when {
          hasTarget(gIndex, secretTarget) && hasTarget(sIndex, guessTarget) -> {
            consume(gIndex, secretTarget)
            consume(sIndex, guessTarget)
          }
          hasTarget(gIndex, secretTarget) -> {
            consume(gIndex, secretTarget)
            store(sIndex, secretTarget)
          }
          hasTarget(sIndex, guessTarget) -> {
            consume(sIndex, guessTarget)
            store(gIndex, guessTarget)
          }
          else -> {
            store(sIndex, secretTarget)
            store(gIndex, guessTarget)
          }
        }
      }
    }
  }

  return Evaluation(rightPosition, wrongPosition)
}

fun evaluateGuess33(secret: String, guess: String): Evaluation {
  fun evaluateRight(secret: String, guess: String): Int {
    fun calculate(secret: String, guess: String, index: Int = 0): Int {
      return if (index in secret.indices) {
        val matchCount = if (secret[index] == guess[index]) {
          1
        } else {
          0
        }
        return matchCount + calculate(secret, guess, index + 1)
      } else {
        0
      }
    }
    return calculate(secret, guess)
  }

  fun evaluateWrong(secret: String, guess: String): Int {
    fun stripRight(secret: String, guess: String, index: Int = 0): Pair<String, String> {
      return if (index in secret.indices) {
        val unmatchChar = if (secret[index] == guess[index]) {
          Pair("", "")
        } else {
          Pair(secret[index].toString(), guess[index].toString())
        }
        val tail = stripRight(secret, guess, index + 1)
        unmatchChar.first + tail.first to unmatchChar.second + tail.second
      } else {
        Pair("", "")
      }
    }

    fun String.toCountMap(): Map<Char, Int> {
      fun toCountMap(str: String, index: Int = 0): Map<Char, Int> {
        return if (index in str.indices) {
          val char = str[index]
          val tail = toCountMap(str, index + 1)
          tail + mapOf(char to tail.getOrDefault(char, 0) + 1)
        } else {
          emptyMap()
        }
      }
      return toCountMap(this)
    }
    val (s, g) = stripRight(secret, guess)

    val sMap = s.toCountMap()
    var gMap = g.toCountMap()

    return sMap.map { sEntry -> min(sEntry.value, gMap.getOrDefault(sEntry.key, 0)) }
      .sum()
  }
  return Evaluation(evaluateRight(secret, guess), evaluateWrong(secret, guess))
}

fun evaluateGuess(secret: String, guess: String): Evaluation {
  fun evaluateRight(secret: String, guess: String): Int {
    return secret.zip(guess).count { it.first == it.second }
  }

  fun evaluateWrong(secret: String, guess: String): Int {

    fun String.toCountMap(): Map<Char, Int> {
      return this.groupBy { it }.map { it.key to it.value.size }.toMap()
    }
    val (s, g) = secret.zip(guess).filter { it.first != it.second }.map { it.first }.joinToString() to
            secret.zip(guess).filter { it.first != it.second }.map { it.second }.joinToString()

    val sMap = s.toCountMap()
    var gMap = g.toCountMap()

    return sMap.map { sEntry -> min(sEntry.value, gMap.getOrDefault(sEntry.key, 0)) }
      .sum()
  }
  return Evaluation(evaluateRight(secret, guess), evaluateWrong(secret, guess))
}
