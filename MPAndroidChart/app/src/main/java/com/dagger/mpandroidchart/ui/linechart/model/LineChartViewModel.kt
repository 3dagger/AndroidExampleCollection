package com.dagger.mpandroidchart.ui.linechart.model

import com.dagger.mpandroidchart.Constants
import com.dagger.mpandroidchart.base.BaseViewModel
import com.dagger.mpandroidchart.repository.data.RemoteService
import com.dagger.mpandroidchart.ui.linechart.LineChartNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LineChartViewModel(private val remoteService: RemoteService) : BaseViewModel<LineChartNavigator.View>(), LineChartNavigator.ViewModel{

    fun onLoadPersonalBikeReport(period: String) {
        addDisposable(remoteService.apiLoadPersonalBikeReport(token = Constants.JWT_TOKEN, period = period)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
//                Logger.d("it :: ${it.body()}")
            }, {

            }))
    }

    override fun disposableClear() {
        onCleared()
    }
}