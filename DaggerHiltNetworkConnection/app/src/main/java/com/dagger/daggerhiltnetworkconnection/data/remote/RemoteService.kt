package com.dagger.daggerhiltnetworkconnection.data.remote

import com.dagger.daggerhiltnetworkconnection.ApiData
import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteService {
    @GET("${ApiData.API_GITHUB_USER_INFO}{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): Response<UserInfo>
}