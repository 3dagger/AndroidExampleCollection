package com.dagger.daggerhiltnetworkconnection

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun foo(): Flow<Int> = flow {
    for(i in 1..3) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking {
    launch {
        for(k in 1..3) {
            println("not bolocked $k")
            delay(100)
        }
    }

    foo().collect { value -> print(value) }
}