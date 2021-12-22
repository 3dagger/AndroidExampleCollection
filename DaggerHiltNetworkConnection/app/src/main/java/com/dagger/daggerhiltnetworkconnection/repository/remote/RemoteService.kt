package com.dagger.daggerhiltnetworkconnection.repository.remote

import com.dagger.daggerhiltnetworkconnection.ApiData
import com.dagger.daggerhiltnetworkconnection.data.remote.TermsList
import retrofit2.Response
import retrofit2.http.GET

interface RemoteService {
    @GET(ApiData.API_AGREEMENT_TERMS_LIST)
    suspend fun getAgreementTermsList(): Response<TermsList>
}