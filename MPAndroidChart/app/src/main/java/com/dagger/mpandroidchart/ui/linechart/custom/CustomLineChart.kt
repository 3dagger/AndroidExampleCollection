package com.dagger.mpandroidchart.ui.linechart.custom

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider

/**
 * <pre>
  * com.dagger.mpandroidchart.ui.linechart.custom
  * </pre>
  *
  * @author : 이수현
  * @Date : 2021/12/21 2:31 오후
  * @Version : 1.0.0
  * @Description : LineChart CustomView
  * @History :
  *
  **/
class CustomLineChart : BarLineChartBase<LineData?>, LineDataProvider {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun init() {
        super.init()
        mRenderer = CustomLineChartRenderer(context, this, mAnimator, mViewPortHandler)
    }

    override fun getLineData(): LineData {
        return mData!!
    }

    override fun onDetachedFromWindow() {
        if (mRenderer != null && mRenderer is CustomLineChartRenderer) {
            (mRenderer as CustomLineChartRenderer).releaseBitmap()
        }
        super.onDetachedFromWindow()
    }
}
