package com.dagger.daggerhiltnetworkconnection

import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

suspend fun performRequest(request: Int): String {
    delay(1000)
    return "response $request"
}

fun main() = runBlocking<Unit> {
    (1..3).asFlow()
        .map { request -> println(request) }
        .collect { response -> println(response) }
}
