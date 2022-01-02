package com.dagger.daggerhiltnetworkconnection.data.remote

import com.dagger.daggerhiltnetworkconnection.domain.main.entity.MainUserInfoEntity
import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfoResponse
import com.dagger.daggerhiltnetworkconnection.domain.main.repository.MainRepository
import com.dagger.daggerhiltnetworkconnection.data.network.status.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val remoteService: RemoteService,
    private val responseToUserInfo: (ApiResponse<UserInfoResponse>) -> ApiResponse<MainUserInfoEntity>
) :
    MainRepository {
//    override fun getUserInfo(owner: String?): Flow<ApiResponse<UserInfo>> = flow {
//        val response = remoteService.getUserInfo(owner?: "3dagger")
//        emit(response)
//    }

//    override fun getUserInfo(owner: String?): Flow<ApiResponse<UserInfo>> = flow {
//        val response = remoteService.getUserInfo1(owner?: "3dagger")
//        emit(responseToUserInfo(response))
//    }

    override fun getUserInfo(owner: String?): Flow<MainUserInfoEntity> = flow {
        emit(remoteService.getUserInfo(owner?: "3dagger"))
    }



}