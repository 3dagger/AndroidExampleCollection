package com.dagger.daggerhiltnetworkconnection.presentation.main

import androidx.lifecycle.*
import com.dagger.daggerhiltnetworkconnection.base.BaseViewModel
import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfo
import com.dagger.daggerhiltnetworkconnection.domain.model.UserRepo
import com.dagger.daggerhiltnetworkconnection.domain.repository.MainRepository
import com.dagger.daggerhiltnetworkconnection.domain.usecase.MainUseCase
import com.dagger.daggerhiltnetworkconnection.network.status.ApiResponse
import com.dagger.daggerhiltnetworkconnection.utils.Resource
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainUseCase: MainUseCase) : BaseViewModel() {
    private val _userInfo: MutableLiveData<Resource<UserInfo>> = MutableLiveData()
    val userInfo: MutableLiveData<Resource<UserInfo>>
        get() = _userInfo


    fun searchUserInfoResult(owner: String?) {
        mainUseCase.execute(owner)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
            .filterIsInstance<ApiResponse.Success<UserInfo>>()
            .map {
                Logger.d("it.data :: ${it.data}")
                it.data
            }
            .asLiveData()
    }

//    fun getUserInfo(userId: String?) {
//        viewModelScope.launch {
//            mainUseCase.getUserInfo(userId = userId?: "3dagger", onStart = { handleLoading(true) }, onComplete = { handleLoading(false) }, onError = { handleLoading(false)})
//                .onEach {
//                    _userInfo.value = it
//                }
//                .launchIn(viewModelScope)
//        }
//    }


//    private val _userRepositories = MutableLiveData<List<UserRepo>>()
//    val userRepositories: LiveData<List<UserRepo>>
//        get() = _userRepositories
//
//    fun getUserRepositories(owner: String?) {
//        viewModelScope.launch {
//            mainUseCase.getUserRepo(owner = owner)
//                .onEach {
//                    _userRepositories.value = it
//                }
//                .launchIn(viewModelScope)
//        }
//    }

}