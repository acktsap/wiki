package acktsap.fixture

import com.navercorp.fixturemonkey.kotlin.KFixtureMonkey
import com.navercorp.fixturemonkey.kotlin.giveMeOne

fun main() {
    val fixtureMonkey = KFixtureMonkey.create()
    val fixture = fixtureMonkey.giveMeOne<String>()
    println(fixture)
}
