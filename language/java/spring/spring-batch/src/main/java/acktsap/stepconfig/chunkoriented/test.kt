package acktsap.stepconfig.chunkoriented

import org.springframework.batch.core.JobExecution

class test {
}

fun main() {
    val jobExecution = JobExecution(0L)
    val lastUpdated = jobExecution.lastUpdated
    lastUpdated?.let { it.time }
}
