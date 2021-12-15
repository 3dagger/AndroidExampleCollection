package com.dagger.mpandroidchart.ui.linechart

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import com.dagger.mpandroidchart.R
import com.dagger.mpandroidchart.base.BaseActivity
import com.dagger.mpandroidchart.databinding.ActivityLinechartBinding
import com.dagger.mpandroidchart.ui.linechart.model.LineChartViewModel
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.renderer.LineChartRenderer
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject
import java.text.DecimalFormat

class LineChartActivity : BaseActivity<ActivityLinechartBinding, LineChartViewModel>(), LineChartNavigator.View {
    override val viewModel: LineChartViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_linechart

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@LineChartActivity)
    }

    override fun onProcess() {
        initViewSetup()
        subscribeObservers()
    }

    private fun initViewSetup() {
        onPrepareLineChartSecond()
    }

    private fun subscribeObservers() {
        viewModel.batteryUsageEntryData.observe(this@LineChartActivity, Observer {
            onPrepareLineChartFirst(list = it)
        })
    }

    private fun onPrepareLineChartFirst(list: ArrayList<Entry>) {
        viewDataBinding.lineChart.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawGridBackground(false)//격자구조 넣을건지

            axisLeft.run { //왼쪽 축. 즉 Y방향 축을 뜻한다.
                setDrawLabels(false) // 값 적는거 허용 (0, 50, 100)
                setDrawGridLines(false) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                setDrawBorders(false) // 외각 라인 설정
            }

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에다가 둔다.
                setDrawAxisLine(false) // 축 그림
                setDrawGridLines(true) // 격자
                setLabelCount(7, true)
                gridLineWidth = 2f
                gridColor = Color.parseColor("#c1c1c1")
                textColor = ContextCompat.getColor(context,R.color.black) //라벨 색상
                textSize = 12f // 텍스트 크기
                valueFormatter = MyXAxisFormatter3()
            }

            legend.isEnabled = false //차트 범례 설정
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
        }

        val set = LineDataSet(list,"DataSet").apply {
            color = Color.parseColor("#0091c1")
            setCircleColor(Color.parseColor("#0091c1"))
            circleHoleColor = Color.parseColor("#0091c1")
            circleRadius = 3f
            lineWidth = 3f
            fillDrawable = ContextCompat.getDrawable(this@LineChartActivity, R.drawable.test_gradient)
            valueTextSize = 10f
            valueTextColor = Color.parseColor("#3c3c3c")
            valueTypeface = ResourcesCompat.getFont(this@LineChartActivity, R.font.notosanskrbold)
            setDrawFilled(true)
            setDrawValues(true)
        }

        val dataSet : ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(set)

        val data = LineData(dataSet)

        viewDataBinding.lineChart.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            invalidate()
        }
    }

    /**
     * @author : 이수현
     * @Date : 2021/12/15 10:54 오전
     * @Description :
     * @History :
     *
     **/
    private fun onPrepareLineChartSecond() {
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f,4.0f))
        entries.add(Entry(2f,10.0f))
        entries.add(Entry(3f,6.0f))
        entries.add(Entry(4f,2.0f))
        entries.add(Entry(5f,8.0f))
        entries.add(Entry(6f,6.0f))
        entries.add(Entry(7f,0.0f))

        viewDataBinding.lineChart2.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawGridBackground(false)//격자구조 넣을건지

            axisLeft.run { //왼쪽 축. 즉 Y방향 축을 뜻한다.
                granularity = 100f // 50 단위마다 선을 그리려고 설정.
                setDrawLabels(false) // 값 적는거 허용 (0, 50, 100)
                setDrawGridLines(false) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                setDrawBorders(false) // 외각 라인 설정
            }

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에다가 둔다.
                granularity = 1f // 1 단위만큼 간격 두기
                setDrawAxisLine(false) // 축 그림
                setDrawGridLines(true) // 격자

                gridLineWidth = 2f
                gridColor = Color.parseColor("#c1c1c1")

                textColor = ContextCompat.getColor(context,R.color.black) //라벨 색상
                textSize = 12f // 텍스트 크기
                valueFormatter = MyXAxisFormatter() // X축 라벨값(밑에 표시되는 글자) 바꿔주기 위해 설정
            }

            legend.isEnabled = false //차트 범례 설정
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
        }

        var set = LineDataSet(entries,"DataSet").apply {
            color = Color.parseColor("#c1c1c1")
            setCircleColor(Color.parseColor("#c1c1c1"))
            fillDrawable = ContextCompat.getDrawable(this@LineChartActivity, R.drawable.test_gradient)
            circleHoleColor = Color.parseColor("#c1c1c1")
            circleRadius = 3f
            valueTextColor = ContextCompat.getColor(this@LineChartActivity, R.color.black)
            valueTextSize = 9f
            lineWidth = 3f
        }

        val dataSet : ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(set)

        val data = LineData(dataSet)

        viewDataBinding.lineChart2.run {
            this.data = data
            invalidate()
        }
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }

    inner class MyXAxisFormatter : ValueFormatter() {
        private val days = arrayOf("12.01","12.02","12.03","12.04","12.05","12.06","12.07")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt()-1) ?: value.toString()
        }
    }

    inner class MyXAxisFormatter2(private val xValue : ArrayList<String>) : ValueFormatter() {
        private val days = arrayOf("12.01","12.02","12.03","12.04","12.05","12.06","12.07")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt()-1) ?: value.toString()
        }
    }


    inner class MyXAxisFormatter3 : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return DecimalFormat("00.00").format(value)
        }
    }


}


