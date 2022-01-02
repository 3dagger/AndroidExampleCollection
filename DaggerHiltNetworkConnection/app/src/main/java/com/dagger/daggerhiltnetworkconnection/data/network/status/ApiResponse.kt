package com.dagger.daggerhiltnetworkconnection.data.network.status

sealed class ApiResponse<T> {

    class Success<T>(val data: T, val code: Int) : ApiResponse<T>()

    class Loading<T> : ApiResponse<T>()

    class ApiError<T>(val message: String, val code: Int) : ApiResponse<T>()

    class NetworkError<T>(val throwable: Throwable) : ApiResponse<T>()

    class NullResult<T> : ApiResponse<T>()
}
