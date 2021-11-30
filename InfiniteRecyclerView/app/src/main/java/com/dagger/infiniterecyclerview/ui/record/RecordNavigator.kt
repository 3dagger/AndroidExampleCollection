package com.dagger.infiniterecyclerview.ui.record

interface RecordNavigator {
    interface View {

    }
    interface ViewModel {
        fun disposableClear()
    }
}