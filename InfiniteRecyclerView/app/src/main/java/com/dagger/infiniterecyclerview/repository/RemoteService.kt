package com.dagger.infiniterecyclerview.repository

import com.dagger.infiniterecyclerview.model.BaeminDummyData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    @GET("contents?typeCode=notice&size=10")
    fun onLoadNotice(@Query("page") page: Int): Single<Response<BaeminDummyData>>
}