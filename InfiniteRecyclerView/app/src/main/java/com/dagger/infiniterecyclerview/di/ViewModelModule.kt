package com.dagger.infiniterecyclerview.di

import com.dagger.infiniterecyclerview.ui.main.model.MainViewModel
import com.dagger.infiniterecyclerview.ui.record.model.RecordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModules = module {
    viewModel { MainViewModel(get()) }
    viewModel { RecordViewModel(get()) }
}