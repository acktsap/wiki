package legacy

/*

3 + x = 3 // algebra
x <- 0

Any: Primitive type in java

User
Unit: Void in java

Nothing: Void in java


Nullability (matching (--?), Nullable type)

Any?

User?
Unit?

Nothing?

 */

fun printOut(s: String) {

}

fun main() {
  val s1: String = "always not null"
  val s2: String? = null
  val s3: String? = "diddle"

  printOut(s1)

  // error
//  printOut(s2)

  // same as
  /*
  if (null == s3) {
    null
  } else {
    s3.length
  }
   */
  val s4: Int? = s3?.length

  // String! or String? whatoever
//  val javaValue = JavaTypes.hello
//  val a: String = javaValue
//
//  // String?
//  val b = JavaTypes.godHello


  // alvis, do if it's not null, default as ""
  val s5 = s2 ?: ""

  // 3항연산자
  s2.takeIf { s -> true } ?: "default"


  // type casting
//  val i1: Int = s1 as Int

  // safe casting
  val any1 = "Hello"
  if (any1 is String) {
    any1.substring(3, 5)
  }
  // java form
//  if (any1 instanceof String) {
//    ((String)any1).substring(3, 5)
//  }

  // smart casting
  (any1 as String)?.substring(3, 5)

  // forced casting (please do not use)
  // it's slow
//  s2!!::length


  val value: String? = "Hello, world"
  println(value as? Int) // safe casting
//  println(value as Int?) // error
  println(value as? String) // safe casting
  println(value as String?) // no error
}

