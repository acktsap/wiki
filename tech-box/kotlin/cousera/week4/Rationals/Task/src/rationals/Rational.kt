package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger
import java.math.BigInteger.ZERO

data class Rational(val numerator: BigInteger, val denominator: BigInteger) {
  override fun toString(): String = when (this.denominator) {
    BigInteger.ONE -> this.numerator.toString(10)
    else -> "${this.numerator.toString(10)}/${this.denominator.toString(10)}"
  }
}

// factories

infix fun Int.divBy(other: Int): Rational {
  return BigInteger.valueOf(this.toLong()) divBy BigInteger.valueOf(other.toLong())
}

infix fun Long.divBy(other: Long): Rational {
  return BigInteger.valueOf(this) divBy BigInteger.valueOf(other)
}

infix fun BigInteger.divBy(other: BigInteger): Rational = when {
  other != ZERO -> {
    val left = this.abs()
    val right = other.abs()
    val gcd = left.gcd(right)
    val numerator = left.div(gcd)
    val denominator = right.div(gcd)
    val sign = BigInteger.valueOf((this.signum() * other.signum()).toLong())
    Rational(numerator.multiply(sign), denominator)
  }
  this == ZERO -> Rational(BigInteger.ZERO, BigInteger.ONE)
  else -> throw IllegalArgumentException()
}

fun String.toRational(): Rational {
  val splited = this.split("/")
  return when (splited.size) {
    1 -> BigInteger(splited[0]) divBy BigInteger.ONE
    2 -> BigInteger(splited[0]) divBy BigInteger(splited[1])
    else -> throw IllegalArgumentException()
  }
}

// operators

operator fun Rational.plus(other: Rational): Rational {
  val n = (this.numerator * other.denominator) + (other.numerator * this.denominator)
  val d = this.denominator * other.denominator
  return n divBy d
}

operator fun Rational.minus(other: Rational): Rational {
  val n = (this.numerator * other.denominator) - (other.numerator * this.denominator)
  val d = this.denominator * other.denominator
  return n divBy d
}

operator fun Rational.times(other: Rational): Rational {
  val n = this.numerator * other.numerator
  val d = this.denominator * other.denominator
  return n divBy d
}

operator fun Rational.div(other: Rational): Rational {
  val n = this.numerator * other.denominator
  val d = this.denominator * other.numerator
  return n divBy d
}

operator fun Rational.unaryMinus(): Rational = this.numerator.negate() divBy this.denominator

operator fun Rational.compareTo(other: Rational): Int =
  (this.numerator * other.denominator).compareTo(other.numerator * this.denominator)

operator fun Iterable<Rational>.contains(other: Rational): Boolean = when (this.count()) {
  2 -> this.first() <= other && other <= this.last()
  else -> throw IllegalArgumentException()
}

operator fun Rational.rangeTo(other: Rational): Iterable<Rational> = when {
  this <= other -> listOf(this, other)
  else -> throw IllegalArgumentException()
}

fun main() {
  val half = 1 divBy 2
  val third = 1 divBy 3

  val sum: Rational = half + third
  println(5 divBy 6 == sum)

  val difference: Rational = half - third
  println(1 divBy 6 == difference)

  val product: Rational = half * third
  println(1 divBy 6 == product)

  val quotient: Rational = half / third
  println(3 divBy 2 == quotient)

  val negation: Rational = -half
  println(-1 divBy 2 == negation)

  println((2 divBy 1).toString() == "2")
  println((-2 divBy 4).toString() == "-1/2")
  println("117/1098".toRational().toString() == "13/122")

  val twoThirds = 2 divBy 3
  println(half < twoThirds)

  println(half in third..twoThirds)

  println(2000000000L divBy 4000000000L == 1 divBy 2)

  println(
    "912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
  )
}