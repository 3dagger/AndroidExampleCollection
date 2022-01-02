package com.dagger.daggerhiltnetworkconnection.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val _isLoading by lazy { MutableLiveData(false) }
    val isLoading: LiveData<Boolean> by lazy { _isLoading }

    fun handleLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }
}

//abstract class BaseViewModel : ViewModel() {
//    private val _isLoading = MutableLiveData<Boolean>(false)
//    val isLoading: LiveData<Boolean>
//        get() = _isLoading
//
//    fun showProgress() {
//        _isLoading.postValue(true)
//    }
//
//    fun dismissProgress() {
//        _isLoading.postValue(false)
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//    }
//}