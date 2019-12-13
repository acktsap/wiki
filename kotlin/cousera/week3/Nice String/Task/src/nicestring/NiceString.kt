package nicestring

const val unMatch = 0
const val match = 3
const val threshold = match * 2
val notAfterB = setOf('u', 'a', 'e')
val vowels = setOf('a', 'e', 'i', 'o', 'u')

fun String.isNiceLim(): Boolean {
  if (this.isEmpty()) {
    return false
  } else {
    val testC1 = { originC1: Int, pre: Char, curr: Char ->
      if (originC1 == match && pre == 'b' && notAfterB.contains(curr)) {
        unMatch
      } else {
        originC1
      }
    }
    val testC2 = { originC2: Int, curr: Char ->
      if (originC2 != match && vowels.contains(curr)) {
        originC2 + 1
      } else {
        originC2
      }
    }
    val testC3 = { originC3: Int, pre: Char, curr: Char ->
      if (originC3 != match && pre == curr) {
        match
      } else {
        originC3
      }
    }

    fun test(pre: Char?, i: Int, c1: Int, c2: Int, c3: Int): Boolean {
      return when {
        this.length <= i -> (c1 + c2 + c3) >= threshold
        (c2 + c3) >= threshold -> true
        pre == null -> test(this[i], i + 1, c1, testC2(c2, this[i]), c3)
        else -> {
          val nextC1 = testC1(c1, pre, this[i])
          val nextC2 = testC2(c2, this[i])
          val nextC3 = testC3(c3, pre, this[i])
          test(this[i], i + 1, nextC1, nextC2, nextC3)
        }
      }
    }
    return test(null, 0, match, unMatch, unMatch)
  }

}

fun String.isNice(): Boolean {
  val c1 = listOf("bu", "ba", "be").none { it in this }
  val c2 = 3 <= "aeiou".sumBy { a -> this.count { a == it } }
  val c3 = (0 until length - 1).map { this[it] to this[it + 1] }.any { it.first == it.second }
  return listOf(c1, c2, c3).count { it } >= 2
}