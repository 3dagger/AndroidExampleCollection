package com.dagger.daggerhiltnetworkconnection.domain.repository

import com.dagger.daggerhiltnetworkconnection.base.BaseApiResponse
import com.dagger.daggerhiltnetworkconnection.data.remote.RemoteService
import javax.inject.Inject

//class MainRepository @Inject constructor(private val mainUseCase: MainUseCase, private val ioDispatcher: CoroutineDispatcher) {
class MainRepository @Inject constructor(private val remoteService: RemoteService): BaseApiResponse() {

    suspend fun getUserInfo(userId: String) = remoteService.getUserInfo(userId)

}