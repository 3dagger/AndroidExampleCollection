package kr.dagger.stateflowexample.di

import kr.dagger.stateflowexample.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class HttpRequestInterceptor : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {
		val tmdbKey = BuildConfig.TMDB_KEY
		val originalRequest = chain.request()
		val request = originalRequest.newBuilder()
			.addHeader(name = "accept", value = "application/json")
			.addHeader(name = "Authorization", value = tmdbKey)
			.build()
		return chain.proceed(request)
	}
}