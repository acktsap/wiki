package acktsap.jvm

import acktsap.Block
import java.util.*

fun main() {
    // TODO: https://kotlinlang.org/docs/java-interop.html

    Block("Optional<Int?>가 의미가 있는가?") {
        val intValue: Int? = 3
        // val optional1 = Optional.of(intValue) // Int? 안받는다고 에러
        val optional2 = Optional.ofNullable(intValue) // Optional<Int>가 됨
        // val optional3: Optional<Int?> = Optional.ofNullable(intValue) // Optional<Int> 리턴이라서 타입 받아도 안됨
    }
}
