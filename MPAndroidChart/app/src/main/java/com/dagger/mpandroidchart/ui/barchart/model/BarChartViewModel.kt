package com.dagger.mpandroidchart.ui.barchart.model

import com.dagger.mpandroidchart.base.BaseViewModel
import com.dagger.mpandroidchart.repository.data.RemoteService
import com.dagger.mpandroidchart.ui.barchart.BarChartNavigator

class BarChartViewModel(private val remoteService: RemoteService) : BaseViewModel<BarChartNavigator.View>(), BarChartNavigator.ViewModel {
    override fun disposableClear() {
        onCleared()
    }
}