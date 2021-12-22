package com.dagger.daggerhiltnetworkconnection.repository.remote

import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MainRepository @Inject constructor(private val remoteClient: RemoteClient, private val ioDispatcher: CoroutineDispatcher) : Repository {


    fun fetchTermsList() {

    }
}