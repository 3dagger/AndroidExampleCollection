package com.dagger.infiniterecyclerview.ui.record

import android.os.Bundle
import com.dagger.infiniterecyclerview.R
import com.dagger.infiniterecyclerview.base.BaseActivity
import com.dagger.infiniterecyclerview.databinding.ActivityRecordBinding
import com.dagger.infiniterecyclerview.ui.record.model.RecordViewModel
import org.koin.android.ext.android.inject

class RecordActivity : BaseActivity<ActivityRecordBinding, RecordViewModel>(), RecordNavigator.View {
    override val viewModel: RecordViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_record

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@RecordActivity)
    }

    override fun onProcess() {
        viewModel.onLoadRecordData()

    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}