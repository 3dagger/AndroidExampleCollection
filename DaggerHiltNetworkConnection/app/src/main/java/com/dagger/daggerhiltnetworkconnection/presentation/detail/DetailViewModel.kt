package com.dagger.daggerhiltnetworkconnection.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dagger.daggerhiltnetworkconnection.base.BaseViewModel
import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfo
import com.dagger.daggerhiltnetworkconnection.domain.model.UserRepo
import com.dagger.daggerhiltnetworkconnection.domain.usecase.MainUseCase
import com.dagger.daggerhiltnetworkconnection.utils.Resource
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val mainUseCase: MainUseCase) : BaseViewModel() {
    private val _userInfo: MutableLiveData<Resource<UserInfo>> = MutableLiveData()
    val userInfo: MutableLiveData<Resource<UserInfo>>
        get() = _userInfo



    fun getUserInfo(userId: String?) {
        viewModelScope.launch {
            mainUseCase.getUserInfo(userId = userId?: "3dagger", onStart = {  }, onComplete = {  }, onError = { Logger.d("onError")})
                .onEach {
                    Logger.d("userInfo :: $it")
                    _userInfo.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    private val _userRepositories = MutableLiveData<Resource<List<UserRepo>>>()
    val userRepositories: LiveData<Resource<List<UserRepo>>>
        get() = _userRepositories

    fun getUserRepositories(owner: String?) {
        viewModelScope.launch {
            mainUseCase.getUserRepo(owner = owner)
                .onEach {
                    _userRepositories.value = it
                }
                .launchIn(viewModelScope)
        }
    }

}