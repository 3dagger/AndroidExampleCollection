package com.dagger.mpandroidchart.repository.data

import com.dagger.mpandroidchart.model.ReportData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RemoteService {
    //@GET("${ApiData.API_PAYMENTS}{tid}")
    //    fun apiPaymentsResult(@Header("X-AUTH-TOKEN") token: String, @Path("tid") tid: String?) : Single<Response<PaymentResult>>
    @GET("https://api-dev.aims-rnd.com/api/rent/report/{period}")
    fun apiLoadPersonalBikeReport(@Header("X-AUTH-TOKEN") token: String, @Path("period") period: String) : Single<Response<ReportData>>
}