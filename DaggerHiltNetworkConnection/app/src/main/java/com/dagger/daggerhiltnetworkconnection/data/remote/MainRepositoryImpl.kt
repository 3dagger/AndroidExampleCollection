package com.dagger.daggerhiltnetworkconnection.data.remote

import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfo
import com.dagger.daggerhiltnetworkconnection.domain.repository.MainRepository
import com.dagger.daggerhiltnetworkconnection.network.status.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(private val remoteService: RemoteService) : MainRepository {
    override fun getUserInfo(owner: String?): Flow<ApiResponse<UserInfo>> = flow {
        val response = remoteService.getUserInfo(owner?: "3dagger")
        emit(response)
    }


}