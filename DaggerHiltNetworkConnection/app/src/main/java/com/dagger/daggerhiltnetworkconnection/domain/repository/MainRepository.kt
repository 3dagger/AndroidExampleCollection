package com.dagger.daggerhiltnetworkconnection.domain.repository

import com.dagger.daggerhiltnetworkconnection.base.BaseApiResponse
import com.dagger.daggerhiltnetworkconnection.data.remote.RemoteService
import javax.inject.Inject

class MainRepository @Inject constructor(private val remoteService: RemoteService): BaseApiResponse() {

    suspend fun getUserRepos(owner: String?) = remoteService.getUserRepos(owner?: "3dagger")

    suspend fun getUserInfo(userId: String) = remoteService.getUserInfo(userId)

}