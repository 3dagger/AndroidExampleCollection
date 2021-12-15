package com.dagger.mpandroidchart.ui.linechart

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.dagger.mpandroidchart.R
import com.dagger.mpandroidchart.base.BaseActivity
import com.dagger.mpandroidchart.databinding.ActivityLinechartBinding
import com.dagger.mpandroidchart.ui.barchart.BarChartActivity
import com.dagger.mpandroidchart.ui.linechart.model.LineChartViewModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

class LineChartActivity : BaseActivity<ActivityLinechartBinding, LineChartViewModel>(), LineChartNavigator.View {
    override val viewModel: LineChartViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_linechart

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@LineChartActivity)
    }

    override fun onProcess() {
        initViewSetup()
    }

    private fun initViewSetup() {
        onPrepareLineChartFirst()
        onPrepareLineChartSecond()
    }

    private fun onPrepareLineChartFirst() {
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f,50.0f))
        entries.add(Entry(2f,60.0f))
        entries.add(Entry(3f,49.0f))
        entries.add(Entry(4f,30.0f))
        entries.add(Entry(5f,0.0f))
        entries.add(Entry(6f,50.0f))
        entries.add(Entry(7f,60.0f))

        viewDataBinding.lineChart.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
//            setMaxVisibleValueCount(7) // 최대 보이는 그래프 개수를 7개로 지정
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawGridBackground(false)//격자구조 넣을건지

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에다가 둔다.
//                granularity = 1f // 1 단위만큼 간격 두기
                setDrawAxisLine(false) // 축 그림
                setDrawGridLines(true) // 격자

                gridLineWidth = 2f
                gridColor = Color.parseColor("#c1c1c1")

                textColor = ContextCompat.getColor(context,R.color.black) //라벨 색상
                textSize = 12f // 텍스트 크기
                valueFormatter = MyXAxisFormatter() // X축 라벨값(밑에 표시되는 글자) 바꿔주기 위해 설정
            }

            axisLeft.run { //왼쪽 축. 즉 Y방향 축을 뜻한다.
//                granularity = 100f // 50 단위마다 선을 그리려고 설정.
                setDrawLabels(false) // 값 적는거 허용 (0, 50, 100)
                setDrawGridLines(false) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                setDrawBorders(false) // 외각 라인 설정
            }

            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
//                animateY(1000) // 밑에서부터 올라오는 애니매이션 적용

            legend.isEnabled = false //차트 범례 설정
        }

        var set = LineDataSet(entries,"DataSet").apply {
            color = Color.parseColor("#0091c1")
            circleRadius = 3f

            setCircleColor(Color.parseColor("#0091c1"))
            circleHoleColor = Color.parseColor("#0091c1")
            lineWidth = 3f
            fillDrawable = ContextCompat.getDrawable(this@LineChartActivity, R.drawable.test_gradient)

//            valueTextSize = 10f
//            valueTextColor = Color.parseColor("#000000")

            Logger.d("values :: $values")

            setDrawFilled(true)
            setDrawIcons(true)
            setDrawValues(true)
//                fillFormatter =
//                    IFillFormatter { dataSet, dataProvider ->
//                        viewDataBinding.lineChart.axisLeft.axisMinimum
//                    }
        }

        val dataSet : ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(set)


        LineData(dataSet).apply {
            setValueTextColor(Color.BLACK)
            setValueTextSize(12F)
        }
        val data = LineData(dataSet)

        viewDataBinding.lineChart.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            invalidate()
        }
    }

    private fun onPrepareLineChartSecond() {
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f,80.0f))
        entries.add(Entry(2f,100.0f))
        entries.add(Entry(3f,109.0f))
        entries.add(Entry(4f,110.0f))
        entries.add(Entry(5f,0.0f))
        entries.add(Entry(6f,50.0f))
        entries.add(Entry(7f,105.0f))

        viewDataBinding.lineChart2.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
            setMaxVisibleValueCount(7) // 최대 보이는 그래프 개수를 7개로 지정
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
            circleHoleColor = Color.parseColor("#c1c1c1")
            circleRadius = 3f
            valueTextColor = ContextCompat.getColor(this@LineChartActivity, R.color.black)
            valueTextSize = 9f
            lineWidth = 3f
        }// 데이터셋 초기화
//        set.apply {
//            color = Color.parseColor("#c1c1c1")
//            setCircleColor(Color.parseColor("#c1c1c1"))
//            circleHoleColor = Color.parseColor("#c1c1c1")
//            circleRadius = 3f
//            valueTextColor = ContextCompat.getColor(this@LineChartActivity, R.color.black)
//            valueTextSize = 9f
//            lineWidth = 3f
//        }


        val dataSet : ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(set)

        val data = LineData(dataSet)

        viewDataBinding.lineChart2.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            notifyDataSetChanged()
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
}