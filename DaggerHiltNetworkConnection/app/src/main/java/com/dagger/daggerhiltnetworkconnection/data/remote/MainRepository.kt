package com.dagger.daggerhiltnetworkconnection.data.remote

import com.dagger.daggerhiltnetworkconnection.base.BaseDataSource
import com.dagger.daggerhiltnetworkconnection.repository.remote.RemoteService
import com.dagger.daggerhiltnetworkconnection.utils.performGetOperation
import javax.inject.Inject

class MainRepository @Inject constructor(private val remoteService: RemoteService): BaseDataSource(){
    fun fetchTermsList() = getResult { remoteService.getAgreementTermsList() }
//suspend fun fetchTermsList() = performGetOperation(
//
//  networkCall = remoteService.getAgreementTermsList()
//)
}