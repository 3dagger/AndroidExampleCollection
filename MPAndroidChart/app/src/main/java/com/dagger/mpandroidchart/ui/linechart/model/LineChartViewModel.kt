package com.dagger.mpandroidchart.ui.linechart.model

import android.R.attr
import androidx.lifecycle.MutableLiveData
import com.dagger.mpandroidchart.Constants
import com.dagger.mpandroidchart.base.BaseViewModel
import com.dagger.mpandroidchart.model.ReportData
import com.dagger.mpandroidchart.repository.data.RemoteService
import com.dagger.mpandroidchart.ui.linechart.LineChartNavigator
import com.github.mikephil.charting.data.Entry
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.R.attr.data




class LineChartViewModel(private val remoteService: RemoteService) : BaseViewModel<LineChartNavigator.View>(), LineChartNavigator.ViewModel{
    private val _bikeReportData = MutableLiveData<ReportData>()
    val bikeReportData : MutableLiveData<ReportData>
        get() = _bikeReportData

    val entryData = ArrayList<Entry>()
    val lastEntryData = ArrayList<Entry>()
    val dayData = ArrayList<String>()

    private val _batteryUsageEntryData = MutableLiveData<ArrayList<Entry>>()
    val batteryUsageEntryData : MutableLiveData<ArrayList<Entry>>
        get() = _batteryUsageEntryData

    var batteryChangeCount = MutableLiveData<Int>()
    var socUseCount = MutableLiveData<Int>()




    init {
        onLoadPersonalBikeReport(period = "day")
    }

    fun onLoadPersonalBikeReport(period: String) {
        addDisposable(remoteService.apiLoadPersonalBikeReport(token = Constants.JWT_TOKEN, period = period)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _bikeReportData.value = it.body()

                val a = arrayListOf<Int>()
                a.clear()
                it.body()?.socReport!!.mapValues { (key, value) -> a.add(value) }
                socUseCount.value = a.last()


                val b = arrayListOf<Int>()
                b.clear()
                it.body()?.countReport!!.mapValues { (key, value) -> b.add(value) }
                batteryChangeCount.value = b.last()
                
                
                onPrepareBatteryUsageEntryData(reportData = it.body()!!)
            }, {

            }))
    }


    fun onPrepareBatteryUsageEntryData(reportData: ReportData) {
        entryData.clear()
        lastEntryData.clear()

        reportData.socReport.forEach { (key, value) ->
            entryData.add(Entry(key.toFloat(), value.toFloat()))
        }
        lastEntryData.add(entryData[entryData.lastIndex])

        _batteryUsageEntryData.value = entryData
    }

    fun onLoadMockReport() {
        addDisposable(remoteService.apiMockBikeReport()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.d("mock api :: ${it.body()}")
                _bikeReportData.value = it.body()
                onPrepareBatteryUsageEntryData(reportData = it.body()!!)
            }, {

            }))
    }





    override fun disposableClear() {
        onCleared()
    }
}