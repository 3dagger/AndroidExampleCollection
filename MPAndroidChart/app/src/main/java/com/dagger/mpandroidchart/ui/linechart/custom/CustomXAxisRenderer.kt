package com.dagger.mpandroidchart.ui.linechart.custom

import android.graphics.*
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.renderer.AxisRenderer
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.*
import com.orhanobut.logger.Logger
import kotlin.math.roundToInt

/**
 * <pre>
  * com.dagger.mpandroidchart.ui.linechart.custom
  * </pre>
  *
  * @author : 이수현
  * @Date : 2021/12/21 2:10 오후
  * @Version : 1.0.0
  * @Description : XAxis 랜더 클래스(좌표 중 X축 마지막 TextColor 수정 위해 SuperClass 작성)
  * @History :
  *
  **/
open class CustomXAxisRenderer(viewPortHandler: ViewPortHandler?, private var mXAxis: XAxis, trans: Transformer?) : XAxisRenderer(viewPortHandler, mXAxis, trans) {

    init {
        mAxisLabelPaint.color = Color.BLACK
        mAxisLabelPaint.textAlign = Paint.Align.CENTER
        mAxisLabelPaint.textSize = Utils.convertDpToPixel(10f)
    }

    override fun setupGridPaint() {
        mGridPaint.color = mXAxis.gridColor
        mGridPaint.strokeWidth = mXAxis.gridLineWidth
        mGridPaint.pathEffect = mXAxis.gridDashPathEffect
    }

    override fun computeAxis(min: Float, max: Float, inverted: Boolean) {
        var min = min
        var max = max
        if (mViewPortHandler.contentWidth() > 10 && !mViewPortHandler.isFullyZoomedOutX) {
            val p1 = mTrans.getValuesByTouchPoint(mViewPortHandler.contentLeft(), mViewPortHandler.contentTop())
            val p2 = mTrans.getValuesByTouchPoint(mViewPortHandler.contentRight(), mViewPortHandler.contentTop())
            if (inverted) {
                min = p2.x.toFloat()
                max = p1.x.toFloat()
            } else {
                min = p1.x.toFloat()
                max = p2.x.toFloat()
            }
            MPPointD.recycleInstance(p1)
            MPPointD.recycleInstance(p2)
        }
        computeAxisValues(min, max)
    }

    override fun computeAxisValues(min: Float, max: Float) {
        super.computeAxisValues(min, max)
        computeSize()
    }

    override fun computeSize() {
        val longest = mXAxis.longestLabel
        mAxisLabelPaint.typeface = mXAxis.typeface
        mAxisLabelPaint.textSize = mXAxis.textSize
        val labelSize = Utils.calcTextSize(mAxisLabelPaint, longest)
        val labelWidth = labelSize.width
        val labelHeight = Utils.calcTextHeight(mAxisLabelPaint, "Q").toFloat()
        val labelRotatedSize = Utils.getSizeOfRotatedRectangleByDegrees(labelWidth, labelHeight, mXAxis.labelRotationAngle)
        mXAxis.mLabelWidth = labelWidth.roundToInt()
        mXAxis.mLabelHeight = labelHeight.roundToInt()
        mXAxis.mLabelRotatedWidth = labelRotatedSize.width.roundToInt()
        mXAxis.mLabelRotatedHeight = labelRotatedSize.height.roundToInt()
        FSize.recycleInstance(labelRotatedSize)
        FSize.recycleInstance(labelSize)
    }

    override fun renderAxisLabels(c: Canvas) {
        if (!mXAxis.isEnabled || !mXAxis.isDrawLabelsEnabled) return
        val yoffset = mXAxis.yOffset
        mAxisLabelPaint.typeface = mXAxis.typeface
        mAxisLabelPaint.textSize = mXAxis.textSize
        mAxisLabelPaint.color = mXAxis.textColor

        val pointF = MPPointF.getInstance(0f, 0f)
        when (mXAxis.position) {
            XAxis.XAxisPosition.TOP -> {
                pointF.x = 0.5f
                pointF.y = 1.0f
                drawLabels(c, mViewPortHandler.contentTop() - yoffset, pointF)
            }
            XAxis.XAxisPosition.TOP_INSIDE -> {
                pointF.x = 0.5f
                pointF.y = 1.0f
                drawLabels(c, mViewPortHandler.contentTop() + yoffset + mXAxis.mLabelRotatedHeight, pointF)
            }
            XAxis.XAxisPosition.BOTTOM -> {
                pointF.x = 0.5f
                pointF.y = 0.0f
                drawLabels(c, mViewPortHandler.contentBottom() + yoffset, pointF)
            }
            XAxis.XAxisPosition.BOTTOM_INSIDE -> {
                pointF.x = 0.5f
                pointF.y = 0.0f
                drawLabels(c, mViewPortHandler.contentBottom() - yoffset - mXAxis.mLabelRotatedHeight, pointF)
            }
            else -> {
                pointF.x = 0.5f
                pointF.y = 1.0f
                drawLabels(c, mViewPortHandler.contentTop() - yoffset, pointF)
                pointF.x = 0.5f
                pointF.y = 0.0f
                drawLabels(c, mViewPortHandler.contentBottom() + yoffset, pointF)
            }
        }
        MPPointF.recycleInstance(pointF)
    }

    override fun renderAxisLine(c: Canvas) {
        if (!mXAxis.isDrawAxisLineEnabled || !mXAxis.isEnabled) return
        mAxisLinePaint.color = mXAxis.axisLineColor
        mAxisLinePaint.strokeWidth = mXAxis.axisLineWidth
        mAxisLinePaint.pathEffect = mXAxis.axisLineDashPathEffect
        if (mXAxis.position == XAxis.XAxisPosition.TOP || mXAxis.position == XAxis.XAxisPosition.TOP_INSIDE || mXAxis.position == XAxis.XAxisPosition.BOTH_SIDED) {
            c.drawLine(mViewPortHandler.contentLeft(), mViewPortHandler.contentTop(), mViewPortHandler.contentRight(), mViewPortHandler.contentTop(), mAxisLinePaint)
        }
        if (mXAxis.position == XAxis.XAxisPosition.BOTTOM || mXAxis.position == XAxis.XAxisPosition.BOTTOM_INSIDE || mXAxis.position == XAxis.XAxisPosition.BOTH_SIDED) {
            c.drawLine(mViewPortHandler.contentLeft(), mViewPortHandler.contentBottom(), mViewPortHandler.contentRight(), mViewPortHandler.contentBottom(), mAxisLinePaint)
        }
    }

    override fun drawLabels(c: Canvas?, pos: Float, anchor: MPPointF?) {
        val labelRotationAngleDegrees = mXAxis.labelRotationAngle
        val centeringEnabled = mXAxis.isCenterAxisLabelsEnabled
        val positions = FloatArray(mXAxis.mEntryCount * 2)
        run {
            var i = 0
            while (i < positions.size) {
                if (centeringEnabled) {
                    positions[i] = mXAxis.mCenteredEntries[i / 2]
                } else {
                    positions[i] = mXAxis.mEntries[i / 2]
                }
                i += 2
            }
        }
        mTrans.pointValuesToPixel(positions)
        var i = 0
        while (i < positions.size) {
            var x = positions[i]
            if (mViewPortHandler.isInBoundsX(x)) {
                val label = mXAxis.valueFormatter.getAxisLabel(mXAxis.mEntries[i / 2], mXAxis)

                if (mXAxis.isAvoidFirstLastClippingEnabled) {

                    // avoid clipping of the last
                    if (i / 2 == mXAxis.mEntryCount - 1 && mXAxis.mEntryCount > 1) {
                        val width = Utils.calcTextWidth(mAxisLabelPaint, label).toFloat()
                        if (width > mViewPortHandler.offsetRight() * 2
                            && x + width > mViewPortHandler.chartWidth
                        ) x -= width / 2

                        // avoid clipping of the first
                    } else if (i == 0) {
                        val width = Utils.calcTextWidth(mAxisLabelPaint, label).toFloat()
                        x += width / 2
                    }
                }

                /**
                 * @author : 이수현
                 * @Date : 2021/12/16 10:52 오전
                 * @Description : 포지션 - 2 인 경우 (마지막 값) 컬러 변경
                 * @History :
                 *
                 **/
                if(i == positions.size - 2) mAxisLabelPaint.color = Color.parseColor("#3c3c3c")

                drawLabel(c, label, x, pos, anchor, labelRotationAngleDegrees)
            }
            i += 2
        }
    }

    override fun drawLabel(c: Canvas?, formattedLabel: String?, x: Float, y: Float, anchor: MPPointF?, angleDegrees: Float) {
        Utils.drawXAxisValue(c, formattedLabel, x, y, mAxisLabelPaint, anchor, angleDegrees)
    }

    protected var mRenderGridLinesPath = Path()
    protected var mRenderGridLinesBuffer = FloatArray(2)
    override fun renderGridLines(c: Canvas) {
        if (!mXAxis.isDrawGridLinesEnabled || !mXAxis.isEnabled) return
        val clipRestoreCount = c.save()
        c.clipRect(gridClippingRect!!)
        if (mRenderGridLinesBuffer.size != mAxis.mEntryCount * 2) {
            mRenderGridLinesBuffer = FloatArray(mXAxis.mEntryCount * 2)
        }
        val positions = mRenderGridLinesBuffer
        run {
            var i = 0
            while (i < positions.size) {
                positions[i] = mXAxis.mEntries[i / 2]
                positions[i + 1] = mXAxis.mEntries[i / 2]
                i += 2
            }
        }
        mTrans.pointValuesToPixel(positions)
        setupGridPaint()
        val gridLinePath = mRenderGridLinesPath
        gridLinePath.reset()
        var i = 0
        while (i < positions.size) {
            drawGridLine(c, positions[i], positions[i + 1], gridLinePath)
            i += 2
        }
        c.restoreToCount(clipRestoreCount)
    }

    override fun getGridClippingRect(): RectF? {
        mGridClippingRect.set(mViewPortHandler.contentRect)
        mGridClippingRect.inset(-mAxis.gridLineWidth, 0f)
        return mGridClippingRect
    }

    override fun drawGridLine(c: Canvas, x: Float, y: Float, gridLinePath: Path) {
        gridLinePath.moveTo(x, mViewPortHandler.contentBottom())
        gridLinePath.lineTo(x, mViewPortHandler.contentTop())

        // draw a path because lines don't support dashing on lower android versions
        c.drawPath(gridLinePath, mGridPaint)
        gridLinePath.reset()
    }

    protected var mRenderLimitLinesBuffer = FloatArray(2)
    protected var mLimitLineClippingRect = RectF()

    override fun renderLimitLines(c: Canvas) {
        val limitLines = mXAxis.limitLines
        if (limitLines == null || limitLines.size <= 0) return
        val position = mRenderLimitLinesBuffer
        position[0] = 0F
        position[1] = 0F
        for (i in limitLines.indices) {
            val l = limitLines[i]
            if (!l.isEnabled) continue
            val clipRestoreCount = c.save()
            mLimitLineClippingRect.set(mViewPortHandler.contentRect)
            mLimitLineClippingRect.inset(-l.lineWidth, 0f)
            c.clipRect(mLimitLineClippingRect)
            position[0] = l.limit
            position[1] = 0f
            mTrans.pointValuesToPixel(position)
            renderLimitLineLine(c, l, position)
            renderLimitLineLabel(c, l, position, 2f + l.yOffset)
            c.restoreToCount(clipRestoreCount)
        }
    }

    var mLimitLineSegmentsBuffer = FloatArray(4)
    private val mLimitLinePath = Path()
    override fun renderLimitLineLine(c: Canvas, limitLine: LimitLine, position: FloatArray) {
        mLimitLineSegmentsBuffer[0] = position[0]
        mLimitLineSegmentsBuffer[1] = mViewPortHandler.contentTop()
        mLimitLineSegmentsBuffer[2] = position[0]
        mLimitLineSegmentsBuffer[3] = mViewPortHandler.contentBottom()
        mLimitLinePath.reset()
        mLimitLinePath.moveTo(mLimitLineSegmentsBuffer[0], mLimitLineSegmentsBuffer[1])
        mLimitLinePath.lineTo(mLimitLineSegmentsBuffer[2], mLimitLineSegmentsBuffer[3])
        mLimitLinePaint.style = Paint.Style.STROKE
        mLimitLinePaint.color = limitLine.lineColor
        mLimitLinePaint.strokeWidth = limitLine.lineWidth
        mLimitLinePaint.pathEffect = limitLine.dashPathEffect
        c.drawPath(mLimitLinePath, mLimitLinePaint)
    }

    override fun renderLimitLineLabel(c: Canvas, limitLine: LimitLine, position: FloatArray, yOffset: Float) {
        val label = limitLine.label

        if (label != null && label != "") {
            mLimitLinePaint.style = limitLine.textStyle
            mLimitLinePaint.pathEffect = null
            mLimitLinePaint.color = limitLine.textColor
            mLimitLinePaint.strokeWidth = 0.5f
            mLimitLinePaint.textSize = limitLine.textSize
            val xOffset = limitLine.lineWidth + limitLine.xOffset
            when (limitLine.labelPosition) {
                LimitLine.LimitLabelPosition.RIGHT_TOP -> {
                    val labelLineHeight = Utils.calcTextHeight(mLimitLinePaint, label).toFloat()
                    mLimitLinePaint.textAlign = Paint.Align.LEFT
                    c.drawText(label, position[0] + xOffset, mViewPortHandler.contentTop() + yOffset + labelLineHeight, mLimitLinePaint)
                }
                LimitLine.LimitLabelPosition.RIGHT_BOTTOM -> {
                    mLimitLinePaint.textAlign = Paint.Align.LEFT
                    c.drawText(label, position[0] + xOffset, mViewPortHandler.contentBottom() - yOffset, mLimitLinePaint)
                }
                LimitLine.LimitLabelPosition.LEFT_TOP -> {
                    mLimitLinePaint.textAlign = Paint.Align.RIGHT
                    val labelLineHeight = Utils.calcTextHeight(mLimitLinePaint, label).toFloat()
                    c.drawText(label, position[0] - xOffset, mViewPortHandler.contentTop() + yOffset + labelLineHeight, mLimitLinePaint)
                }
                else -> {
                    mLimitLinePaint.textAlign = Paint.Align.RIGHT
                    c.drawText(label, position[0] - xOffset, mViewPortHandler.contentBottom() - yOffset, mLimitLinePaint)
                }
            }
        }
    }
}
