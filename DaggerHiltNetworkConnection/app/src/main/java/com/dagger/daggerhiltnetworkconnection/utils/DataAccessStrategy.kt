package com.dagger.daggerhiltnetworkconnection.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(networkCall: suspend () -> Resource<A>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
//        val source = databaseQuery.invoke().map { Resource.success(it) }
//        emitSource(source)

        val responseStatus = networkCall.invoke()
        Logger.d("responseStatus data :: ${responseStatus.data}")
        if (responseStatus.status == Resource.Status.SUCCESS) {
//            emit(Resource.success(data = networkCall.invoke().data))
//            Logger.d("responseStatus :: ${responseStatus.status}")
//            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
//            emitSource(source)
        }
    }