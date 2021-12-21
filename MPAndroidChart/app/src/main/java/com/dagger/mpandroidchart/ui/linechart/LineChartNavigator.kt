package com.dagger.mpandroidchart.ui.linechart

interface LineChartNavigator{
    interface View {
        fun showProgress()

        fun dismissProgress()
    }
    interface ViewModel {
        fun disposableClear()
    }
}