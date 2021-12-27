package com.dagger.daggerhiltnetworkconnection.domain.usecase

import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfo
import com.dagger.daggerhiltnetworkconnection.domain.repository.MainRepository
import com.dagger.daggerhiltnetworkconnection.utils.Resource
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class MainUseCase @Inject constructor(private val mainRepository: MainRepository, private val ioDispatcher: CoroutineDispatcher) {

    /**
     * @author : 이수현
     * @Date : 2021/12/27 7:03 오후
     * @Description : 1
     * @History :
     *
     **/
//    fun getTerms(): LiveData<Resource<List<TermsList>>> = performGetOperation(networkCall = { mainClient.getTermsList() })
//    suspend fun getTerms() = mainClient.getTermsList()

    /**
     * @author : 이수현
     * @Date : 2021/12/27 7:03 오후
     * @Description : 2 flow
     * @History :
     *
     **/
//    suspend fun getTerms(): Flow<Resource<List<TermsList>>> = flow {
//        emit(Resource.loading())
//        try{
//            val terms = mainClient.getTermsList()
//            emit(Resource.success(terms.body()!!))
//        }catch (e: Exception) {
////            emit(Resource.error())
//        }
//    }.onStart { Logger.d("start") }.onCompletion { Logger.d("completion") }.flowOn(ioDispatcher)

    /**
     * @author : 이수현
     * @Date : 2021/12/27 7:03 오후
     * @Description : 3
     * @History :
     *
     **/
    suspend fun getUserInfo(userId: String, onStart: () -> Unit, onComplete: () -> Unit, onError: () -> Unit): Flow<Resource<UserInfo>> = flow{
        emit(Resource.loading())
        try{
            val response = mainRepository.getUserInfo(userId)
            Logger.d("response :: $response")
            emit(Resource.success(response.body()!!))
        }catch (e: Exception) {
            Logger.d("error :: ${e.message}")
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

}