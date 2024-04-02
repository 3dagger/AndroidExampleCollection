package kr.dagger.gsontypeadapterexample

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

	private val gson: Gson by lazy {
		GsonBuilder()
			.registerTypeAdapter(CellItem::class.java, CellTypeAdapter())
			.create()
	}

	private val okHttpClient: OkHttpClient by lazy {
		val httpLoggingInterceptor = HttpLoggingInterceptor()
			.setLevel(HttpLoggingInterceptor.Level.BODY)
		OkHttpClient.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.build()
	}

	private val retrofit: Retrofit by lazy {
		Retrofit.Builder()
			.baseUrl("https://jpassets.jobplanet.co.kr/")
			.client(okHttpClient)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()
	}

	val retrofitService: ApiService by lazy {
		retrofit.create(ApiService::class.java)
	}
}