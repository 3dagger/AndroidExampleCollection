package com.dagger.daggerhiltnetworkconnection.data.remote

import com.dagger.daggerhiltnetworkconnection.ApiData
import com.dagger.daggerhiltnetworkconnection.Constants.Companion.PERSONAL_GIT_HUB_TOKEN
import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfo
import com.dagger.daggerhiltnetworkconnection.domain.model.UserRepo
import com.dagger.daggerhiltnetworkconnection.network.status.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface RemoteService {

    @Headers("Authorization: token $PERSONAL_GIT_HUB_TOKEN")
    @GET("${ApiData.API_GITHUB_USER_INFO}{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): ApiResponse<UserInfo>





    @GET(ApiData.API_GITHUB_USER_REPO)
    suspend fun getUserRepos(@Path("owner") owner: String, @Header("Authorization") token: String): List<UserRepo>

    @GET(ApiData.API_GITHUB_USERS)
    suspend fun getUsers(): Response<UserInfo>



    /**
     * @author : 이수현
     * @Date : 2021/12/29 2:48 오전
     * @Description : 유저 레포지토리 조화
     * @History :
     *
     **/
//    @GET("users/{owner}/repos")
//    suspend fun getRepos(@Path("owner") owner: String) : List<GithubRepoRes>


    /**
     *
     */

    //    @GET("user")
    //    suspend fun fetchUserOwner(@Header("Authorization") authorization: String): Response<UserInfo>
    //
    //    @GET("users/{username}/received_events?")
    //    suspend fun queryReceivedEvents(@Path("username") username: String,
    //                                    @Query("page") pageIndex: Int,
    //                                    @Query("per_page") perPage: Int): List<ReceivedEvent>
    //
    //    @GET("users/{username}/repos?")
    //    suspend fun queryRepos(@Path("username") username: String,
    //                           @Query("page") pageIndex: Int,
    //                           @Query("per_page") perPage: Int,
    //                           @Query("sort") sort: String): List<Repo>
    //
    //    @GET("search/repositories")
    //    suspend fun search(@Query("q") q: String,
    //                       @Query("page") pageIndex: Int,
    //                       @Query("per_page") perPage: Int): SearchResult
}