package com.dagger.daggerhiltnetworkconnection.ui.main

import androidx.lifecycle.*
import com.dagger.daggerhiltnetworkconnection.base.BaseViewModel
import com.dagger.daggerhiltnetworkconnection.domain.model.TermsList
import com.dagger.daggerhiltnetworkconnection.domain.repository.MainRepository
import com.dagger.daggerhiltnetworkconnection.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {
    private val _termsList: MutableLiveData<Resource<List<TermsList>>> = MutableLiveData()
    val termsList: MutableLiveData<Resource<List<TermsList>>>
        get() = _termsList

//    var isLoading = MutableLiveData<Boolean>()
//    fun getTermsList() {
//        viewModelScope.launch {
//            repository.getTerms()
//                .onEach { value ->
//                    _termsList.value = value
//                }
//                .launchIn(viewModelScope)
//        }
//    }



}