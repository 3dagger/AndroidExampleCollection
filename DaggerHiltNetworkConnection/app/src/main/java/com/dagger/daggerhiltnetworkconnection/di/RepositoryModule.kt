package com.dagger.daggerhiltnetworkconnection.di

import com.dagger.daggerhiltnetworkconnection.repository.remote.MainRepository
import com.dagger.daggerhiltnetworkconnection.repository.remote.RemoteClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(remoteClient: RemoteClient, coroutineDispatcher: CoroutineDispatcher) : MainRepository {
        return MainRepository(remoteClient, coroutineDispatcher)
    }
}