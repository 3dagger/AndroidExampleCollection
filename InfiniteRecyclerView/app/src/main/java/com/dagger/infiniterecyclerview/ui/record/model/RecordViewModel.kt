package com.dagger.infiniterecyclerview.ui.record.model

import androidx.lifecycle.MutableLiveData
import com.dagger.infiniterecyclerview.base.BaseViewModel
import com.dagger.infiniterecyclerview.model.RecordDummyData
import com.dagger.infiniterecyclerview.repository.RemoteService
import com.dagger.infiniterecyclerview.ui.record.RecordNavigator
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RecordViewModel(private val remoteService: RemoteService) : BaseViewModel<RecordNavigator.View>(), RecordNavigator.ViewModel{
    private val _recordData = MutableLiveData<RecordDummyData>()
    val recordData : MutableLiveData<RecordDummyData>
        get() = _recordData

//    init {
//        onLoadRecordData()
//    }

    fun onLoadRecordData() {
        addDisposable(remoteService.onLoadRecord()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.d("history size :: ${it.body()?.history?.size}")
                _recordData.value = it.body()
                prepareRecordAdapter(recordList = it.body()!!)
            }, {

            }))
    }

    fun prepareRecordAdapter(recordList: RecordDummyData) : MutableList<FamilyModel> {
        val recordModel = mutableListOf<FamilyModel>()
        for(data in recordList.history) {
            recordModel.add(FamilyModel(FamilyModel.PARENT, data))
        }
        Logger.d("recordModel :: $recordModel")
        return recordModel
    }


    override fun disposableClear() {
        onCleared()
    }
}