package com.dagger.mpandroidchart.repository.data

import com.dagger.mpandroidchart.model.ReportData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RemoteService {
    @GET("https://api-dev.aims-rnd.com/api/rent/report/{period}")
    fun apiLoadPersonalBikeReport(@Header("X-AUTH-TOKEN") token: String, @Path("period") period: String) : Single<Response<ReportData>>

    @GET("https://0505fc60-494d-4010-9936-6c5e2398a10c.mock.pstmn.io/report/{period}")
    fun apiMockBikeReport(@Path("period") period: String) : Single<Response<ReportData>>
}