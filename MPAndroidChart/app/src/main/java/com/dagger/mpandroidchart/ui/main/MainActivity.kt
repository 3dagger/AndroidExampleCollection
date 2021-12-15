package com.dagger.mpandroidchart.ui.main

import android.os.Bundle
import com.dagger.mpandroidchart.BR
import com.dagger.mpandroidchart.ChartType
import com.dagger.mpandroidchart.R
import com.dagger.mpandroidchart.base.BaseActivity
import com.dagger.mpandroidchart.base.BaseRecyclerView
import com.dagger.mpandroidchart.databinding.ActivityMainBinding
import com.dagger.mpandroidchart.databinding.ListMainItemBinding
import com.dagger.mpandroidchart.ext.openActivity
import com.dagger.mpandroidchart.listener.RecyclerViewItemClickListener
import com.dagger.mpandroidchart.ui.barchart.BarChartActivity
import com.dagger.mpandroidchart.ui.linechart.LineChartActivity
import com.dagger.mpandroidchart.ui.main.model.MainItem
import com.dagger.mpandroidchart.ui.main.model.MainViewModel
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View, RecyclerViewItemClickListener {
    override val viewModel: MainViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_main


    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@MainActivity)
        viewDataBinding.run {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            vm = viewModel
            itemClickListener = this@MainActivity

            recyclerView.adapter = object : BaseRecyclerView.Adapter<List<MainItem>, ListMainItemBinding>(
                layoutResId = R.layout.list_main_item,
                bindingVariableId = BR.mainItemData
            ){}
        }
    }

    override fun onProcess() {
    }

    override fun onRecyclerItemClick(position: Int) {
        when(position) {
            ChartType.TypeLineChart.idx -> openActivity(LineChartActivity::class.java)
            ChartType.TypeBarChart.idx -> openActivity(BarChartActivity::class.java)
        }
    }

    override fun onRecyclerLongItemClick(position: Int) {
        when(position) {
            ChartType.TypeLineChart.idx -> openActivity(LineChartActivity::class.java)
            ChartType.TypeBarChart.idx -> openActivity(BarChartActivity::class.java)
        }
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}