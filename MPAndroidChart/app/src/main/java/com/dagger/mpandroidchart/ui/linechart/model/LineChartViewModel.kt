package com.dagger.mpandroidchart.ui.linechart.model

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



    init {
//        onLoadPersonalBikeReport(period = "day")
        onLoadMockReport()
    }

    fun onLoadPersonalBikeReport(period: String) {
        addDisposable(remoteService.apiLoadPersonalBikeReport(token = Constants.JWT_TOKEN, period = period)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _bikeReportData.value = it.body()
                onPrepareBatteryUsageEntryData(reportData = it.body()!!)
            }, {

            }))
    }

//    fun onPrepareBatteryUsageEntryData(reportData: ReportData) {
//        entryData.clear()
//        reportData.socReport.forEach { (key, value) -> entryData.add(Entry(key.toFloat(), value.toFloat())) }
//    }


    fun onPrepareBatteryUsageEntryData(reportData: ReportData) {
        entryData.clear()
        lastEntryData.clear()

        reportData.socReport.toSortedMap().forEach { (key, value) ->
            entryData.add(Entry(key.toFloat(), value.toFloat()))
        }
        lastEntryData.add(entryData[entryData.lastIndex])
//        entryData.removeAt(entryData.lastIndex)


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