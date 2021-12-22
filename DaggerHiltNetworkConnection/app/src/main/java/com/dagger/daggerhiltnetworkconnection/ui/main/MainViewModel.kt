package com.dagger.daggerhiltnetworkconnection.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dagger.daggerhiltnetworkconnection.data.remote.MainRepository
import com.dagger.daggerhiltnetworkconnection.data.remote.TermsList
import com.dagger.daggerhiltnetworkconnection.repository.remote.RemoteService
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    }