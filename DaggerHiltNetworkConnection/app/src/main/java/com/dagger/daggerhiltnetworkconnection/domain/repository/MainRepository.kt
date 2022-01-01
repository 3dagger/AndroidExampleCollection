package com.dagger.daggerhiltnetworkconnection.domain.repository

import com.dagger.daggerhiltnetworkconnection.Constants
import com.dagger.daggerhiltnetworkconnection.Constants.Companion.PERSONAL_GIT_HUB_TOKEN
import com.dagger.daggerhiltnetworkconnection.base.BaseApiResponse
import com.dagger.daggerhiltnetworkconnection.data.remote.RemoteService
import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfo
import com.dagger.daggerhiltnetworkconnection.network.status.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MainRepository {
    fun getUserInfo(owner: String?): Flow<ApiResponse<UserInfo>>
}

//class MainRepository @Inject constructor(private val remoteService: RemoteService): BaseApiResponse() {
//
//    suspend fun getUserRepos(owner: String?) = remoteService.getUserRepos(owner?: "3dagger", PERSONAL_GIT_HUB_TOKEN)
//
//    suspend fun getUserInfo(userId: String) = remoteService.getUserInfo(userId)
//
//}


//class MainRepository @Inject constructor(private val remoteService: RemoteService): BaseApiResponse() {
//
//    suspend fun getUserRepos(owner: String?) = remoteService.getUserRepos(owner?: "3dagger", PERSONAL_GIT_HUB_TOKEN)
//
//    suspend fun getUserInfo(userId: String) = remoteService.getUserInfo(userId)
//
//}