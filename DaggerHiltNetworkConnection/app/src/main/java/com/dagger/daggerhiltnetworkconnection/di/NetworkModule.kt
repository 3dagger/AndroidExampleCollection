package com.dagger.daggerhiltnetworkconnection.di

import com.dagger.daggerhiltnetworkconnection.Constants.Companion.BASE_URL
import com.dagger.daggerhiltnetworkconnection.data.remote.MainRepository
import com.dagger.daggerhiltnetworkconnection.repository.remote.RemoteClient
import com.dagger.daggerhiltnetworkconnection.repository.remote.RemoteService
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
//                if(BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRemoteService(retrofit: Retrofit): RemoteService {
        return retrofit.create(RemoteService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteClient(remoteService: RemoteService): RemoteClient {
        return RemoteClient(remoteService)
    }

}