package com.dagger.mpandroidchart.di

import com.dagger.mpandroidchart.ui.barchart.model.BarChartViewModel
import com.dagger.mpandroidchart.ui.linechart.model.LineChartViewModel
import com.dagger.mpandroidchart.ui.main.model.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModules = module {
    viewModel { MainViewModel(get()) }
    viewModel { LineChartViewModel(get()) }
    viewModel { BarChartViewModel(get()) }
}