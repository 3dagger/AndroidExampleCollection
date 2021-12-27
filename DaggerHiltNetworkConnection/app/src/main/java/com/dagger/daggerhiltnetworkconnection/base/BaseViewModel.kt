package com.dagger.daggerhiltnetworkconnection.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun showProgress() {
        _isLoading.value = true
    }

    fun dismissProgress() {
        _isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}