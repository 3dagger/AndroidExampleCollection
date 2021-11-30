package com.dagger.infiniterecyclerview.ui.main

interface MainNavigator {
    interface View {

    }
    interface ViewModel {
        fun onLoadNoticeData(page: Int)

        fun disposableClear()
    }
}