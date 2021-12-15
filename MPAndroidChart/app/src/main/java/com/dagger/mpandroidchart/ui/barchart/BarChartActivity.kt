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
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.android.ext.android.inject

class BarChartActivity : BaseActivity<ActivityBarchartBinding, BarChartViewModel>(), BarChartNavigator.View {
    override val viewModel: BarChartViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_barchart

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@BarChartActivity)
        val lineChart: LineChart = findViewById<View>(R.id.lineChart) as LineChart

        val entries: ArrayList<Entry> = ArrayList()
        entries.add(Entry(20f, 0F))
        entries.add(Entry(11f, 1F))
        entries.add(Entry(10f, 2F))
        entries.add(Entry(2f, 3F))
        entries.add(Entry(18f, 4F))
        entries.add(Entry(9f, 5F))
        entries.add(Entry(16f, 6F))
        entries.add(Entry(5f, 7F))
        entries.add(Entry(3f, 8F))
        entries.add(Entry(7f, 10F))
        entries.add(Entry(9f, 11F))

        val dataset = LineDataSet(entries, "# of Calls")

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

        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawAxisLine(false)
        }


//        val data = LineData(labels, dataset)
        val data = LineData(dataset)
        dataset.setColors(*ColorTemplate.COLORFUL_COLORS) //
        dataset.valueTextColor = Color.RED


        /*dataset.setDrawCubic(true); //선 둥글게 만들기
        dataset.setDrawFilled(true); //그래프 밑부분 색칠*/
        lineChart.data = data
//        lineChart.animateY(5000)
    }

    override fun onProcess() {

    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }


}