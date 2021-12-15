package com.dagger.mpandroidchart.ui.linechart

interface LineChartNavigator{
    interface View {

    }
    interface ViewModel {
        fun disposableClear()
    }
}