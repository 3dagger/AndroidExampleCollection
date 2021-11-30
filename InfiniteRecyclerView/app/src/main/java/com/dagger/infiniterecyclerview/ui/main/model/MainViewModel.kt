package com.dagger.infiniterecyclerview.ui.main.model

import androidx.lifecycle.MutableLiveData
import com.dagger.infiniterecyclerview.repository.RemoteService
import com.dagger.infiniterecyclerview.base.BaseViewModel
import com.dagger.infiniterecyclerview.model.BaeminDummyData
import com.dagger.infiniterecyclerview.model.RecordDummyData
import com.dagger.infiniterecyclerview.ui.main.MainNavigator
import com.google.gson.annotations.SerializedName
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val remoteService: RemoteService) : BaseViewModel<MainNavigator.View>(), MainNavigator.ViewModel {
    private val _noticeData = MutableLiveData<BaeminDummyData>()
    val noticeData : MutableLiveData<BaeminDummyData>
        get() = _noticeData

    private val _recordData = MutableLiveData<RecordDummyData>()
    val recordData : MutableLiveData<RecordDummyData>
        get() = _recordData

    init {
        onLoadNoticeData(page = 1)
        onLoadRecordData()
    }

    override fun onLoadNoticeData(page: Int) {
        addDisposable(remoteService.onLoadNotice(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _noticeData.value = it.body()
            }, {

            }))
    }

    fun onLoadRecordData() {
        addDisposable(remoteService.onLoadRecord()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.d("result :: ${it.body()}")
                _recordData.value = it.body()
            }, {

            }))
    }

    override fun disposableClear() {
        onCleared()
    }
}

