package com.dagger.mpandroidchart.di

import com.dagger.mpandroidchart.repository.data.RemoteService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModules = module { single(createdAtStart = false) {get<Retrofit>().create(RemoteService::class.java)} }