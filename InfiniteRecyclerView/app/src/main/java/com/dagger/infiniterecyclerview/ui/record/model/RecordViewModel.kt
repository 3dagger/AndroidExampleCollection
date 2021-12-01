package com.dagger.infiniterecyclerview.ui.record.model

import androidx.lifecycle.MutableLiveData
import com.dagger.infiniterecyclerview.base.BaseViewModel
import com.dagger.infiniterecyclerview.model.History
import com.dagger.infiniterecyclerview.model.RecordDummyData
import com.dagger.infiniterecyclerview.repository.RemoteService
import com.dagger.infiniterecyclerview.ui.record.RecordNavigator
import com.orhanobut.logger.Logger
import com.pixplicity.easyprefs.library.Prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RecordViewModel(private val remoteService: RemoteService) : BaseViewModel<RecordNavigator.View>(), RecordNavigator.ViewModel{
    private val _recordData = MutableLiveData<RecordDummyData>()
    val recordData : MutableLiveData<RecordDummyData>
        get() = _recordData

    var dumpArray = mutableListOf<History>()

    init {
        onLoadRecordData(page = 1)
    }

    fun onLoadRecordData(page: Int) {
        addDisposable(remoteService.onLoadRecord(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _recordData.value = it.body()
            }, {

            }))
    }

    fun prepareRecordAdapter(recordList: RecordDummyData) : MutableList<FamilyModel> {
        val recordModel = ArrayList<FamilyModel>()

        if(!dumpArray.isNullOrEmpty() && dumpArray[dumpArray.lastIndex].resultDate == recordList.history[0].resultDate) {
            dumpArray.clear()

            for(data in recordList.history) {
                if(data.resultDate != recordList.history[0].resultDate) {
                    recordModel.add(FamilyModel(FamilyModel.PARENT, data))
                }
                for(data2 in data.rentList) {
                    recordModel.add(FamilyModel(FamilyModel.CHILD, data2))
                }
            }
            recordModel.add(FamilyModel(FamilyModel.PROGRESS, " "))
        }else {
            for(data in recordList.history) {
                recordModel.add(FamilyModel(FamilyModel.PARENT, data))
                for(data2 in data.rentList) {
                    recordModel.add(FamilyModel(FamilyModel.CHILD, data2))
                }
            }
            recordModel.add(FamilyModel(FamilyModel.PROGRESS, " "))
        }

        dumpArray = recordList.history.toMutableList()

        return recordModel
    }

    override fun disposableClear() {
        onCleared()
    }
}