package api.test

// grouping 어떻게 잘 했는가?
// a, b group -> why b cannot replaced with a group???
// what condition?
fun main() {

  // flatmap
  fun <T, R> Iterable<T>.map(transformer: (T) -> R): Iterable<R> {
    return this.flatMap {
      listOf(transformer(it))
    }
  }

  fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
    return this.flatMap {
      if (predicate(it)) listOf(it) else emptyList<T>()
    }
  }


  // fold x, reduce o (in general, how about kotlin?)
  fun <T> Iterable<T>.all(predicate: (T) -> Boolean): Boolean {
    return this.fold(true, { acc, curr -> acc && predicate(curr) })
  }

  fun <T> Iterable<T>.any(predicate: (T) -> Boolean): Boolean {
    return this.fold(false, { acc, curr -> acc.takeIf { acc } ?: predicate(curr) })
  }

  fun <T> Iterable<T>.none(predicate: (T) -> Boolean): Boolean {
    return !this.fold(false, { acc, curr -> acc || predicate(curr) })
  }

  fun <T> Iterable<T>.find(predicate: (T) -> Boolean): T? {
    return this.fold(null as T?, { acc, curr -> acc ?: if (predicate(curr)) curr else acc })
  }


  // groupby
  fun <T> Iterable<T>.count(predicate: (T) -> Boolean): Int {
    return groupBy { predicate(it) }.getOrDefault(true, emptyList()).count()
  }



  val list = listOf<String>("1", "2", "3", "4", "5")
  println("Origin list: $list")

  // flat map
  println("Mapped: ${list.map { s -> s.toInt() * 2 }}")
  println("Filtered: ${list.filter { s -> s != "2" }}")

  // fold
  println("All: ${list.all { 0 < it.toInt() }}")
  println("Any: ${list.any { it == "5" }}")
  println("None: ${list.none { it == "6" }}")
  println("Find: ${list.find { it == "5" }}")

  // group by
  println("count: ${list.count { 3 <= it.toInt() }}")
}


