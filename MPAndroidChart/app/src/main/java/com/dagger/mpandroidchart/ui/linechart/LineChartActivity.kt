package com.dagger.mpandroidchart.ui.linechart

import android.graphics.Color
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.dagger.mpandroidchart.R
import com.dagger.mpandroidchart.base.BaseActivity
import com.dagger.mpandroidchart.databinding.ActivityLinechartBinding
import com.dagger.mpandroidchart.ui.linechart.custom.CustomXAxisRenderer
import com.dagger.mpandroidchart.ui.linechart.model.LineChartViewModel
import com.dagger.mpandroidchart.utility.CustomProgress
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject
import java.text.DecimalFormat
import kotlin.math.roundToInt
import com.github.mikephil.charting.utils.ViewPortHandler

import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.lang.StringBuilder


class LineChartActivity : BaseActivity<ActivityLinechartBinding, LineChartViewModel>(), LineChartNavigator.View {
    override val viewModel: LineChartViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_linechart

    private lateinit var customProgress: CustomProgress

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@LineChartActivity)
        customProgress = CustomProgress(context = this@LineChartActivity)

        viewDataBinding.run {
            lifecycleOwner = this@LineChartActivity
            activity = this@LineChartActivity
            vm = viewModel
        }
    }

    override fun onProcess() {
        showProgress()
        initViewSetup()
        subscribeObservers()
    }

    private fun initViewSetup() {
    }

    private fun subscribeObservers() {
        viewModel.isTestBooleanData.observe(this@LineChartActivity, Observer {
            when(it){
                true -> {
                    viewDataBinding.backgroundView.animate().withEndAction {
                        showProgress()
//                        viewModel.onLoadPersonalBikeReport(period = "day")
                        viewModel.onLoadMockReport(period = "day")
                    }.setDuration(200).translationX(0F).start()
                    viewDataBinding.viewTv2.setTextColor(Color.parseColor("#4b4b4b"))
                    viewDataBinding.viewTv2.typeface = ResourcesCompat.getFont(this@LineChartActivity, R.font.notosanskrbold)
                    viewDataBinding.subScriberTv2.setTextColor(Color.parseColor("#909090"))
                    viewDataBinding.subScriberTv2.typeface = ResourcesCompat.getFont(this@LineChartActivity, R.font.notosanskrmedium)
                }
                false -> {
                    viewDataBinding.backgroundView.animate().withEndAction {
                        showProgress()
//                        viewModel.onLoadPersonalBikeReport(period = "month")
                        viewModel.onLoadMockReport(period = "month")
                    }.setDuration(200).translationX(viewDataBinding.guideline1.x).start()
                    viewDataBinding.viewTv2.setTextColor(Color.parseColor("#909090"))
                    viewDataBinding.viewTv2.typeface = ResourcesCompat.getFont(this@LineChartActivity, R.font.notosanskrmedium)
                    viewDataBinding.subScriberTv2.setTextColor(Color.parseColor("#4b4b4b"))
                    viewDataBinding.subScriberTv2.typeface = ResourcesCompat.getFont(this@LineChartActivity, R.font.notosanskrbold)
                }
            }
        })

        viewModel.batteryUsageEntryData.observe(this@LineChartActivity, Observer {
            onPrepareLineChartFirst(list = it, lastItem = viewModel.lastEntryData)
        })
    }


    private fun onPrepareLineChartFirst(list: ArrayList<Entry>, lastItem: ArrayList<Entry>) {
        viewDataBinding.lineChart.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawGridBackground(false)//격자구조 넣을건지

            axisLeft.run { //왼쪽 축. 즉 Y방향 축을 뜻한다.
                setDrawLabels(false) // 값 적는거 허용 (0, 50, 100)
                setDrawGridLines(false) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                setDrawBorders(false) // 외각 라인 설정
                axisMinimum = 0F
            }

            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.

//            val customXAxisRenderer: XAxisRenderer = CustomXAxisRenderer(viewPortHandler, xAxis, getTransformer(YAxis.AxisDependency.LEFT))
//            setXAxisRenderer(customXAxisRenderer)
            setXAxisRenderer(CustomXAxisRenderer(viewPortHandler, xAxis, getTransformer(YAxis.AxisDependency.LEFT)))

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에다가 둔다.
                setDrawAxisLine(false) // 축 그림
                setDrawGridLines(true) // 격자
                setLabelCount(7, true)
                gridLineWidth = 1.5f
                gridColor = Color.parseColor("#80adadad")
                textColor = Color.parseColor("#adadad")
                textSize = 10f // 텍스트 크기
                typeface = ResourcesCompat.getFont(this@LineChartActivity, R.font.notosanskrmedium)
                valueFormatter = MyXAxisFormatter()
            }

            extraTopOffset = 30F
            extraBottomOffset = 20F
            legend.isEnabled = false //차트 범례 설정

            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateX(100)
        }

        // 단위 DP
        val set = LineDataSet(list,"DataSet").apply {
            color = Color.parseColor("#0091c1")
            setCircleColor(Color.parseColor("#0091c1"))
            circleHoleColor = Color.parseColor("#FFFFFF")
            circleRadius = 6f
            circleHoleRadius = 3f
            lineWidth = 4f
            fillDrawable = ContextCompat.getDrawable(this@LineChartActivity, R.drawable.test_gradient)
            valueTextSize = 10f
            valueTextColor = Color.parseColor("#3c3c3c")
            valueTypeface = ResourcesCompat.getFont(this@LineChartActivity, R.font.notosanskrbold)
            valueFormatter = MyYAxisFormatter()

            setDrawFilled(true)
            setDrawValues(true)
        }
        
        val set2 = LineDataSet(lastItem, "DataSet 2").apply {
            color = Color.parseColor("#0091c1")
            setCircleColor(Color.parseColor("#0091c1"))
            circleHoleColor = Color.parseColor("#FFFFFF")
            circleRadius = 9f
            circleHoleRadius = 6f
            valueTextSize = 0f
            valueTextColor = Color.parseColor("#3c3c3c")
            valueTypeface = ResourcesCompat.getFont(this@LineChartActivity, R.font.notosanskrbold)
            valueFormatter = MyYAxisFormatter()


            setDrawFilled(false)
            setDrawValues(false)
        }

        val dataSet : ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(set)
        dataSet.add(set2)

        val data = LineData(dataSet)

        viewDataBinding.lineChart.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            invalidate()
        }
    }

    override fun showProgress() {
        if(!customProgress.isShowing) customProgress.show()
    }

    override fun dismissProgress() {
        if(customProgress.isShowing) customProgress.dismiss()
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }


    inner class MyXAxisFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return DecimalFormat("00.00").format(value)
        }
    }

    inner class MyYAxisFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String  {
            return value.roundToInt().toString()
        }
    }
}