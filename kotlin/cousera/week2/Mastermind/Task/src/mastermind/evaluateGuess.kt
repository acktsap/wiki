package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

// O(n)
fun evaluateGuess(secret: String, guess: String): Evaluation {
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
