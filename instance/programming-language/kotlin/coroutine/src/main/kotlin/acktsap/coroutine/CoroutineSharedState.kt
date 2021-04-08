package acktsap.coroutine

import acktsap.Block
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun main() {
    Block("Mutual exclusion") {
        runBlocking {
            val mutex = Mutex()
            var counter = 0

            withContext(Dispatchers.Default) {
                repeat(1000) {
                    // protect each increment with lock
                    mutex.withLock {
                        counter++
                    }
                }
            }
            println("Counter = $counter")
        }
    }

    // TODO: https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html
}