package acktsap.coroutine

import acktsap.Block
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

fun main() {
    Block("Mutual exclusion") {
        runBlocking {
            val mutex = Mutex()
            var counter = 0

            withContext(Dispatchers.Default) {
                repeat(100) {
                    launch {
                        repeat(100) {
                            // protect each increment with lock
                            mutex.withLock {
                                counter++
                            }
                        }
                    }
                }
            }
            println("Counter = $counter") // 10000
        }
    }

    // TODO: https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html
}
