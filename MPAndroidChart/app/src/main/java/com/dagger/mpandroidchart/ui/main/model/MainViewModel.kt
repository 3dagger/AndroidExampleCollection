package com.dagger.mpandroidchart.ui.main.model

import androidx.lifecycle.MutableLiveData
import com.dagger.mpandroidchart.Constants.Companion.JWT_TOKEN
import com.dagger.mpandroidchart.base.BaseViewModel
import com.dagger.mpandroidchart.repository.data.RemoteService
import com.dagger.mpandroidchart.ui.main.MainNavigator
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val remoteService: RemoteService) : BaseViewModel<MainNavigator.View>(), MainNavigator.ViewModel {
    private val _mainListData = MutableLiveData<List<MainItem>>()
    val mainListData : MutableLiveData<List<MainItem>>
        get() = _mainListData

    init {
        fetchedListData()
    }
    
    private fun fetchedListData() {
        _mainListData.value = listOf(MainItem(title = "Line Chart", contents = "꺾은선 그래프"), MainItem(title = "Bar Chart", contents = "막대 그래프"))
    }

    override fun disposableClear() {
        onCleared()
    }
}