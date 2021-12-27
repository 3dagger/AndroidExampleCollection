package com.dagger.daggerhiltnetworkconnection.base

import com.dagger.daggerhiltnetworkconnection.StatusCode
import com.dagger.daggerhiltnetworkconnection.utils.Resource
import com.orhanobut.logger.Logger
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.SandwichInitializer
import com.skydoves.sandwich.exceptions.NoContentException
import com.skydoves.sandwich.operators.ApiResponseOperator
import com.skydoves.sandwich.operators.ApiResponseSuspendOperator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

abstract class BaseApiResponse {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) return Resource.success(body)
            }
            return error("${response.code()} ${response.message()}")
        }catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("error :: $message")
    }


//    data class Success<T>(val response: Response<T>) : BaseApiResponse<T>() {
//        val statusCode: StatusCode = getStatusCodeFromResponse(response = response)
//        val headers: Headers = response.headers()
//        val raw: okhttp3.Response = response.raw()
//        val data: T by lazy { response.body() ?: throw NoContentException(statusCode.code) }
//
//
//
//        override fun toString(): String = "[ApiResponse.Success](data=$data)"
//    }
//
//    sealed class Failure<T> {
//        data class Error<T>(val response: Response<T>) : BaseApiResponse<T>() {
//            val statusCode: StatusCode = getStatusCodeFromResponse(response)
//            val headers: Headers = response.headers()
//            val raw: okhttp3.Response = response.raw()
//            val errorBody: ResponseBody? = response.errorBody()
//
//            override fun toString(): String = "[ApiResponse.Failure.Error-$statusCode](errorResponse=$response)"
//        }
//
//        data class Exception<T>(val exception: Throwable) : BaseApiResponse<T>() {
//            val message: String? = exception.localizedMessage
//            override fun toString(): String = "[ApiResponse.Failure.Exception](message=$message)"
//        }
//    }

//    companion object {
//        public fun <T> error(ex: Throwable): ApiResponse.Failure.Exception<T> =
//            ApiResponse.Failure.Exception<T>(ex).apply { operate() }
//
//        @JvmSynthetic
//        public inline fun <T> of(successCodeRange: IntRange = SandwichInitializer.successCodeRange, crossinline f: () -> Response<T>): ApiResponse<T> = try {
//            val response = f()
//            if (response.raw().code in successCodeRange) {
//                ApiResponse.Success(response)
//            } else {
//                ApiResponse.Failure.Error(response)
//            }
//        } catch (ex: Exception) {
//            ApiResponse.Failure.Exception(ex)
//        }.operate()
//
//        @PublishedApi
//        @Suppress("UNCHECKED_CAST")
//        internal fun <T> ApiResponse<T>.operate(): ApiResponse<T> = apply {
//            val globalOperator = SandwichInitializer.sandwichOperator ?: return@apply
//            if (globalOperator is ApiResponseOperator<*>) {
//                operator(globalOperator as ApiResponseOperator<T>)
//            } else if (globalOperator is ApiResponseSuspendOperator<*>) {
//                val context = SandwichInitializer.sandwichOperatorContext
//                val supervisorJob = SupervisorJob(context[Job])
//                val scope = CoroutineScope(context + supervisorJob)
//                scope.launch {
//                    suspendOperator(globalOperator as ApiResponseSuspendOperator<T>)
//                }
//            }
//        }
//    }

//    fun <T> getStatusCodeFromResponse(response: Response<T>): StatusCode {
//        return StatusCode.values().find { it.code == response.code() } ?: StatusCode.Unknown
//    }
}