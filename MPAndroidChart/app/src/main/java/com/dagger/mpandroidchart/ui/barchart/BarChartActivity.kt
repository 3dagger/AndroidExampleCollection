package com.dagger.mpandroidchart.ui.barchart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.dagger.mpandroidchart.R
import com.dagger.mpandroidchart.base.BaseActivity
import com.dagger.mpandroidchart.databinding.ActivityBarchartBinding
import com.dagger.mpandroidchart.ui.barchart.model.BarChartViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.android.ext.android.inject

class BarChartActivity : BaseActivity<ActivityBarchartBinding, BarChartViewModel>(), BarChartNavigator.View {
    override val viewModel: BarChartViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_barchart

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@BarChartActivity)

        viewDataBinding.run {
            lifecycleOwner = this@BarChartActivity
            activity = this@BarChartActivity
            vm = viewModel
        }

        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(20f, 0F))
        entries.add(BarEntry(11f, 1F))
        entries.add(BarEntry(10f, 2F))
        entries.add(BarEntry(2f, 3F))
        entries.add(BarEntry(18f, 4F))
        entries.add(BarEntry(9f, 5F))
        entries.add(BarEntry(16f, 6F))
        entries.add(BarEntry(5f, 7F))
        entries.add(BarEntry(3f, 8F))
        entries.add(BarEntry(7f, 10F))
        entries.add(BarEntry(9f, 11F))

        val dataset = BarDataSet(entries, "FirstData")

        val labels = ArrayList<String>()
        labels.add("January")
        labels.add("February")
        labels.add("March")
        labels.add("April")
        labels.add("May")
        labels.add("June")
        labels.add("July")
        labels.add("August")
        labels.add("September")
        labels.add("October")
        labels.add("November")
        labels.add("December")

        viewDataBinding.barChart.apply {
            axisRight.isEnabled = false
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawAxisLine(false)
            }
        }

        val data = BarData(dataset)
        dataset.setColors(*ColorTemplate.COLORFUL_COLORS) //
        dataset.valueTextColor = Color.RED

        viewDataBinding.barChart.data = data
    }

    override fun onProcess() {

    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }


}