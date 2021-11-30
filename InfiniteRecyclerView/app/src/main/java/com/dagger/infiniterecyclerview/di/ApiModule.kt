package com.dagger.infiniterecyclerview.di

import com.dagger.infiniterecyclerview.repository.RemoteService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModules = module {
    single(createdAtStart = false) { get<Retrofit>().create(RemoteService::class.java)}
}