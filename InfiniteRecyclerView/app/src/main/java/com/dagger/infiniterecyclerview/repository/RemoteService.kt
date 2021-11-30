package com.dagger.infiniterecyclerview.repository

import com.dagger.infiniterecyclerview.model.BaeminDummyData
import com.dagger.infiniterecyclerview.model.RecordDummyData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    @GET("contents?typeCode=notice&size=10")
    fun onLoadNotice(@Query("page") page: Int): Single<Response<BaeminDummyData>>

    @GET("https://0505fc60-494d-4010-9936-6c5e2398a10c.mock.pstmn.io/record")
    fun onLoadRecord(): Single<Response<RecordDummyData>>
}