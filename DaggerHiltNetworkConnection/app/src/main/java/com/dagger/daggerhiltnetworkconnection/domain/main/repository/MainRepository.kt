package com.dagger.daggerhiltnetworkconnection.domain.main.repository

import com.dagger.daggerhiltnetworkconnection.domain.main.entity.MainUserInfoEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getUserInfo(owner: String?): Flow<MainUserInfoEntity>
//    fun getUserInfo(owner: String?): Flow<ApiResponse<UserInfo>>
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