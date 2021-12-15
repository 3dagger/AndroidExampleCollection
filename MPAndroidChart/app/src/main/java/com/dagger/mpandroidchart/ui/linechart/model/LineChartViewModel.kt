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

    val dayData = ArrayList<String>()

    private val _batteryUsageEntryData = MutableLiveData<ArrayList<Entry>>()
    val batteryUsageEntryData : MutableLiveData<ArrayList<Entry>>
        get() = _batteryUsageEntryData

    init {
        onLoadPersonalBikeReport(period = "day")
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


    // Entry x value 는 0 1 2 3 4 5 6 사이즈 만큼 추가해주고 Formatter에서 값을 수정해야 함
    fun onPrepareBatteryUsageEntryData(reportData: ReportData) {
        Logger.d("prepare")
        entryData.clear()
        dayData.clear()
//        Logger.d("convert :: ${reportData.socReport.toSortedMap()}\nraw :: ${reportData}")

        reportData.socReport.toSortedMap().forEach { (key, value) ->
            entryData.add(Entry(key.toFloat(), value.toFloat()))
            dayData.add(key)
        }

        _batteryUsageEntryData.value = entryData
    }





    override fun disposableClear() {
        onCleared()
    }
}