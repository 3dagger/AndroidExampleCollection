package com.dagger.mpandroidchart.ui.linechart.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.IDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.renderer.LineRadarRenderer
import com.github.mikephil.charting.utils.*
import java.lang.ref.WeakReference
import java.util.HashMap
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.dagger.mpandroidchart.R
import com.orhanobut.logger.Logger

/**
 * <pre>
  * com.dagger.mpandroidchart.ui.linechart.custom
  * </pre>
  *
  * @author : 이수현
  * @Date : 2021/12/21 2:27 오후
  * @Version : 1.0.0
  * @Description : Value Background 수정하기 위해 Superclass 작성
  * @History :
  *
  **/
open class CustomLineChartRenderer(private var context: Context, private var mChart: LineDataProvider, animator: ChartAnimator?, viewPortHandler: ViewPortHandler?) : LineRadarRenderer(animator, viewPortHandler) {
    protected var mCirclePaintInner: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mDrawBitmap: WeakReference<Bitmap?>? = null
    private var mBitmapCanvas: Canvas? = null
    private var mBitmapConfig = Bitmap.Config.ARGB_8888
    private var cubicPath = Path()
    private var cubicFillPath = Path()

    init {
        mCirclePaintInner.style = Paint.Style.FILL
        mCirclePaintInner.color = Color.WHITE
    }

    override fun initBuffers() {}

    override fun drawData(c: Canvas) {
        val width = mViewPortHandler.chartWidth.toInt()
        val height = mViewPortHandler.chartHeight.toInt()
        var drawBitmap = if (mDrawBitmap == null) null else mDrawBitmap!!.get()
        if (drawBitmap == null || drawBitmap.width != width || drawBitmap.height != height) {
            if (width > 0 && height > 0) {
                drawBitmap = Bitmap.createBitmap(width, height, mBitmapConfig)
                mDrawBitmap = WeakReference(drawBitmap)
                mBitmapCanvas = Canvas(drawBitmap)
            } else return
        }
        drawBitmap!!.eraseColor(Color.TRANSPARENT)
        val lineData = mChart.lineData
        for (set in lineData.dataSets) {
            if (set.isVisible) drawDataSet(c, set)
        }
        c.drawBitmap(drawBitmap, 0f, 0f, mRenderPaint)
    }

    private fun drawDataSet(c: Canvas?, dataSet: ILineDataSet) {
        if (dataSet.entryCount < 1) return
        mRenderPaint.strokeWidth = dataSet.lineWidth
        mRenderPaint.pathEffect = dataSet.dashPathEffect
        when (dataSet.mode) {
            LineDataSet.Mode.LINEAR, LineDataSet.Mode.STEPPED -> drawLinear(c, dataSet)
            LineDataSet.Mode.CUBIC_BEZIER -> drawCubicBezier(dataSet)
            LineDataSet.Mode.HORIZONTAL_BEZIER -> drawHorizontalBezier(dataSet)
            else -> drawLinear(c, dataSet)
        }
        mRenderPaint.pathEffect = null
    }

    private fun drawHorizontalBezier(dataSet: ILineDataSet) {
        val phaseY = mAnimator.phaseY
        val trans = mChart.getTransformer(dataSet.axisDependency)
        mXBounds[mChart] = dataSet
        cubicPath.reset()
        if (mXBounds.range >= 1) {
            var prev = dataSet.getEntryForIndex(mXBounds.min)
            var cur = prev

            cubicPath.moveTo(cur.x, cur.y * phaseY)
            for (j in mXBounds.min + 1..mXBounds.range + mXBounds.min) {
                prev = cur
                cur = dataSet.getEntryForIndex(j)
                val cpx = (prev.x + (cur.x - prev.x) / 2.0f)
                cubicPath.cubicTo(
                    cpx, prev.y * phaseY,
                    cpx, cur.y * phaseY,
                    cur.x, cur.y * phaseY
                )
            }
        }
        if (dataSet.isDrawFilledEnabled) {
            cubicFillPath.reset()
            cubicFillPath.addPath(cubicPath)
            drawCubicFill(mBitmapCanvas, dataSet, cubicFillPath, trans, mXBounds)
        }
        mRenderPaint.color = dataSet.color
        mRenderPaint.style = Paint.Style.STROKE
        trans.pathValueToPixel(cubicPath)
        mBitmapCanvas!!.drawPath(cubicPath, mRenderPaint)
        mRenderPaint.pathEffect = null
    }

    private fun drawCubicBezier(dataSet: ILineDataSet) {
        val phaseY = mAnimator.phaseY
        val trans = mChart.getTransformer(dataSet.axisDependency)
        mXBounds[mChart] = dataSet
        val intensity = dataSet.cubicIntensity
        cubicPath.reset()
        if (mXBounds.range >= 1) {
            var prevDx = 0f
            var prevDy = 0f
            var curDx = 0f
            var curDy = 0f
            val firstIndex = mXBounds.min + 1
            val lastIndex = mXBounds.min + mXBounds.range
            var prevPrev: Entry?
            var prev = dataSet.getEntryForIndex(Math.max(firstIndex - 2, 0))
            var cur = dataSet.getEntryForIndex(Math.max(firstIndex - 1, 0))
            var next = cur
            var nextIndex = -1
            if (cur == null) return

            // let the spline start
            cubicPath.moveTo(cur.x, cur.y * phaseY)
            for (j in mXBounds.min + 1..mXBounds.range + mXBounds.min) {
                prevPrev = prev
                prev = cur
                cur = if (nextIndex == j) next else dataSet.getEntryForIndex(j)
                nextIndex = if (j + 1 < dataSet.entryCount) j + 1 else j
                next = dataSet.getEntryForIndex(nextIndex)
                prevDx = (cur!!.x - prevPrev!!.x) * intensity
                prevDy = (cur.y - prevPrev.y) * intensity
                curDx = (next.x - prev!!.x) * intensity
                curDy = (next.y - prev.y) * intensity
                cubicPath.cubicTo(
                    prev.x + prevDx, (prev.y + prevDy) * phaseY,
                    cur.x - curDx,
                    (cur.y - curDy) * phaseY, cur.x, cur.y * phaseY
                )
            }
        }

        if (dataSet.isDrawFilledEnabled) {
            cubicFillPath.reset()
            cubicFillPath.addPath(cubicPath)
            drawCubicFill(mBitmapCanvas, dataSet, cubicFillPath, trans, mXBounds)
        }
        mRenderPaint.color = dataSet.color
        mRenderPaint.style = Paint.Style.STROKE
        trans.pathValueToPixel(cubicPath)
        mBitmapCanvas!!.drawPath(cubicPath, mRenderPaint)
        mRenderPaint.pathEffect = null
    }

    private fun drawCubicFill(c: Canvas?, dataSet: ILineDataSet, spline: Path, trans: Transformer, bounds: XBounds) {
        val fillMin = dataSet.fillFormatter
            .getFillLinePosition(dataSet, mChart)
        spline.lineTo(dataSet.getEntryForIndex(bounds.min + bounds.range).x, fillMin)
        spline.lineTo(dataSet.getEntryForIndex(bounds.min).x, fillMin)
        spline.close()
        trans.pathValueToPixel(spline)
        val drawable = dataSet.fillDrawable
        if (drawable != null) {
            drawFilledPath(c, spline, drawable)
        } else {
            drawFilledPath(c, spline, dataSet.fillColor, dataSet.fillAlpha)
        }
    }

    private var mLineBuffer = FloatArray(4)
    private fun drawLinear(c: Canvas?, dataSet: ILineDataSet) {
        val entryCount = dataSet.entryCount
        val isDrawSteppedEnabled = dataSet.mode == LineDataSet.Mode.STEPPED
        val pointsPerEntryPair = if (isDrawSteppedEnabled) 4 else 2
        val trans = mChart.getTransformer(dataSet.axisDependency)
        val phaseY = mAnimator.phaseY
        mRenderPaint.style = Paint.Style.STROKE
        var canvas: Canvas? = null

        canvas = if (dataSet.isDashedLineEnabled) {
            mBitmapCanvas
        } else {
            c
        }
        mXBounds[mChart] = dataSet

        if (dataSet.isDrawFilledEnabled && entryCount > 0) {
            drawLinearFill(c, dataSet, trans, mXBounds)
        }

        if (dataSet.colors.size > 1) {
            if (mLineBuffer.size <= pointsPerEntryPair * 2) mLineBuffer =
                FloatArray(pointsPerEntryPair * 4)
            for (j in mXBounds.min..mXBounds.range + mXBounds.min) {
                var e: Entry? = dataSet.getEntryForIndex(j) ?: continue
                mLineBuffer[0] = e?.x!!
                mLineBuffer[1] = e.y * phaseY
                if (j < mXBounds.max) {
                    e = dataSet.getEntryForIndex(j + 1)
                    if (e == null) break
                    if (isDrawSteppedEnabled) {
                        mLineBuffer[2] = e.x
                        mLineBuffer[3] = mLineBuffer[1]
                        mLineBuffer[4] = mLineBuffer[2]
                        mLineBuffer[5] = mLineBuffer[3]
                        mLineBuffer[6] = e.x
                        mLineBuffer[7] = e.y * phaseY
                    } else {
                        mLineBuffer[2] = e.x
                        mLineBuffer[3] = e.y * phaseY
                    }
                } else {
                    mLineBuffer[2] = mLineBuffer[0]
                    mLineBuffer[3] = mLineBuffer[1]
                }
                trans.pointValuesToPixel(mLineBuffer)
                if (!mViewPortHandler.isInBoundsRight(mLineBuffer[0])) break

                if (!mViewPortHandler.isInBoundsLeft(mLineBuffer[2])
                    || !mViewPortHandler.isInBoundsTop(mLineBuffer[1]) && !mViewPortHandler
                        .isInBoundsBottom(mLineBuffer[3])
                ) continue

                mRenderPaint.color = dataSet.getColor(j)
                canvas!!.drawLines(mLineBuffer, 0, pointsPerEntryPair * 2, mRenderPaint)
            }
        } else {
            if (mLineBuffer.size < (entryCount * pointsPerEntryPair).coerceAtLeast(pointsPerEntryPair) * 2) mLineBuffer = FloatArray((entryCount * pointsPerEntryPair).coerceAtLeast(pointsPerEntryPair) * 4)
            var e1: Entry?
            var e2: Entry?
            e1 = dataSet.getEntryForIndex(mXBounds.min)
            if (e1 != null) {
                var j = 0
                for (x in mXBounds.min..mXBounds.range + mXBounds.min) {
                    e1 = dataSet.getEntryForIndex(if (x == 0) 0 else x - 1)
                    e2 = dataSet.getEntryForIndex(x)
                    if (e1 == null || e2 == null) continue
                    mLineBuffer[j++] = e1.x
                    mLineBuffer[j++] = e1.y * phaseY
                    if (isDrawSteppedEnabled) {
                        mLineBuffer[j++] = e2.x
                        mLineBuffer[j++] = e1.y * phaseY
                        mLineBuffer[j++] = e2.x
                        mLineBuffer[j++] = e1.y * phaseY
                    }
                    mLineBuffer[j++] = e2.x
                    mLineBuffer[j++] = e2.y * phaseY
                }
                if (j > 0) {
                    trans.pointValuesToPixel(mLineBuffer)
                    val size =
                        Math.max((mXBounds.range + 1) * pointsPerEntryPair, pointsPerEntryPair) * 2
                    mRenderPaint.color = dataSet.color
                    canvas!!.drawLines(mLineBuffer, 0, size, mRenderPaint)
                }
            }
        }
        mRenderPaint.pathEffect = null
    }

    /**
     * @author : 이수현
     * @Date : 2021/12/21 1:51 오후
     * @Description :
     * @History :
     *
     **/
    private var mGenerateFilledPathBuffer = Path()
    private fun drawLinearFill(c: Canvas?, dataSet: ILineDataSet, trans: Transformer, bounds: XBounds) {
        val filled = mGenerateFilledPathBuffer
        val startingIndex = bounds.min
        val endingIndex = bounds.range + bounds.min
        val indexInterval = 128
        var currentStartIndex = 0
        var currentEndIndex = indexInterval
        var iterations = 0

        do {
            currentStartIndex = startingIndex + iterations * indexInterval
            currentEndIndex = currentStartIndex + indexInterval
            currentEndIndex = if (currentEndIndex > endingIndex) endingIndex else currentEndIndex
            if (currentStartIndex <= currentEndIndex) {
                generateFilledPath(dataSet, currentStartIndex, currentEndIndex, filled)
                trans.pathValueToPixel(filled)
                val drawable = dataSet.fillDrawable
                if (drawable != null) {
                    drawFilledPath(c, filled, drawable)
                } else {
                    drawFilledPath(c, filled, dataSet.fillColor, dataSet.fillAlpha)
                }
            }
            iterations++
        } while (currentStartIndex <= currentEndIndex)
    }

    private fun generateFilledPath(dataSet: ILineDataSet, startIndex: Int, endIndex: Int, outputPath: Path) {
        val fillMin = dataSet.fillFormatter.getFillLinePosition(dataSet, mChart)
        val phaseY = mAnimator.phaseY
        val isDrawSteppedEnabled = dataSet.mode == LineDataSet.Mode.STEPPED
        outputPath.reset()
        val entry = dataSet.getEntryForIndex(startIndex)
        outputPath.moveTo(entry.x, fillMin)
        outputPath.lineTo(entry.x, entry.y * phaseY)

        var currentEntry: Entry? = null
        var previousEntry = entry
        for (x in startIndex + 1..endIndex) {
            currentEntry = dataSet.getEntryForIndex(x)
            if (isDrawSteppedEnabled) {
                outputPath.lineTo(currentEntry.x, previousEntry!!.y * phaseY)
            }
            outputPath.lineTo(currentEntry.x, currentEntry.y * phaseY)
            previousEntry = currentEntry
        }

        if (currentEntry != null) {
            outputPath.lineTo(currentEntry.x, fillMin)
        }
        outputPath.close()
    }

    override fun drawValues(c: Canvas) {
        if (isDrawingValuesAllowed(mChart)) {
            val dataSets = mChart.lineData.dataSets
            for (i in dataSets.indices) {
                val dataSet = dataSets[i]
                if (!shouldDrawValues(dataSet) || dataSet.entryCount < 1) continue

                applyValueTextStyle(dataSet)
                val trans = mChart.getTransformer(dataSet.axisDependency)

                var valOffset = (dataSet.circleRadius * 1.75f).toInt()
                if (!dataSet.isDrawCirclesEnabled) valOffset /= 2
                mXBounds[mChart] = dataSet
                val positions = trans.generateTransformedValuesLine(dataSet, mAnimator.phaseX, mAnimator.phaseY, mXBounds.min, mXBounds.max)
                val formatter = dataSet.valueFormatter
                val iconsOffset = MPPointF.getInstance(dataSet.iconsOffset)
                iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x)
                iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y)
                var j = 0
                while (j < positions.size) {
                    val x = positions[j]
                    val y = positions[j + 1]
                    if (!mViewPortHandler.isInBoundsRight(x)) break
                    if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y)) {
                        j += 2
                        continue
                    }
                    val entry = dataSet.getEntryForIndex(j / 2 + mXBounds.min)
                    
                    /**
                     * @author : 이수현
                     * @Date : 2021/12/21 2:23 오후
                     * @Description : DrawValue 분기 처리
                     * @History : 
                     *
                     **/
                    if (dataSet.isDrawValuesEnabled) {
                        if(j == positions.size - 2) {
                            customDrawValues(c, formatter.getPointLabel(entry), x, y - valOffset, dataSet.getValueTextColor(j / 2))
                        }else {
                            drawValue(c, formatter.getPointLabel(entry), x, y - valOffset, dataSet.getValueTextColor(j / 2))
                        }
//                        drawValue(c, formatter.getPointLabel(entry), x, y - valOffset, dataSet.getValueTextColor(j / 2))
                    }

                    if (entry.icon != null && dataSet.isDrawIconsEnabled) {
                        val icon = entry.icon
                        Utils.drawImage(c, icon, (x + iconsOffset.x).toInt(), (y + iconsOffset.y).toInt(), icon.intrinsicWidth, icon.intrinsicHeight)
                    }
                    j += 2
                }

                MPPointF.recycleInstance(iconsOffset)
            }

        }
    }

    /**
     * @author : 이수현
     * @Date : 2021/12/21 1:52 오후
     * @Description : 커스텀 Value Background  ->  Value 자릿수에 따라 Left, Right Spread 
     * @History :
     *
     **/
    private val mDrawableBoundsCache = Rect()
    private fun customDrawImage(canvas: Canvas, drawable: Drawable, x: Int, y: Int, width: Int, height: Int, textLength: Int) {
        val drawOffset = MPPointF.getInstance()
        drawOffset.x =(x -(width / 2)).toFloat()
        drawOffset.y =(y -(height / 2)).toFloat()
        drawable.copyBounds(mDrawableBoundsCache)
        when(textLength) {
            1       -> drawable.setBounds(mDrawableBoundsCache.left, mDrawableBoundsCache.top, mDrawableBoundsCache.left + width, mDrawableBoundsCache.top + width) 
            2       -> drawable.setBounds(mDrawableBoundsCache.left - 10, mDrawableBoundsCache.top, mDrawableBoundsCache.left + width + 10, mDrawableBoundsCache.top + width)
            3       -> drawable.setBounds(mDrawableBoundsCache.left - 13, mDrawableBoundsCache.top, mDrawableBoundsCache.left + width + 13, mDrawableBoundsCache.top + width)
            else    -> drawable.setBounds(mDrawableBoundsCache.left, mDrawableBoundsCache.top, mDrawableBoundsCache.left + width, mDrawableBoundsCache.top + width)
        }
        
        val saveId = canvas.save()
        canvas.translate(drawOffset.x, drawOffset.y)
        drawable.draw(canvas)
        canvas.restoreToCount(saveId)
    }

    /**
     * @author : 이수현
     * @Date : 2021/12/16 4:24 오후
     * @Description : 마지막 아이템 수정 메서드(추가)
     * @History :
     *
     **/
    private fun customDrawValues(c: Canvas, valueText: String, x: Float, y: Float, color: Int) {
        ContextCompat.getDrawable(context, R.drawable.chart_point_item_background)?.let { customDrawImage(c, it, x.toInt(), y.toInt() - 40, 55, 30, valueText.length) }
        mValuePaint.color = color
        c.drawText(valueText, x, y - 20, mValuePaint)
    }

    /**
     * @author : 이수현
     * @Date : 2021/12/21 1:52 오후
     * @Description : 수정
     * @History :
     *
     **/
    override fun drawValue(c: Canvas, valueText: String, x: Float, y: Float, color: Int) {
        ContextCompat.getDrawable(context, R.drawable.chart_item_background)?.let { customDrawImage(c, it, x.toInt(), y.toInt() - 40, 55, 30, valueText.length) }
        mValuePaint.color = color
        c.drawText(valueText, x, y - 20, mValuePaint)
    }
    
    override fun drawExtras(c: Canvas) {
        drawCircles(c)
    }
    
    private val mImageCaches = HashMap<IDataSet<*>, DataSetImageCache>()
    private val mCirclesBuffer = FloatArray(2)
    private fun drawCircles(c: Canvas) {
        mRenderPaint.style = Paint.Style.FILL
        val phaseY = mAnimator.phaseY
        mCirclesBuffer[0] = 0F
        mCirclesBuffer[1] = 0F
        val dataSets = mChart.lineData.dataSets
        for (i in dataSets.indices) {
            val dataSet = dataSets[i]
            if (!dataSet.isVisible || !dataSet.isDrawCirclesEnabled || dataSet.entryCount == 0) continue
            mCirclePaintInner.color = dataSet.circleHoleColor
            val trans = mChart.getTransformer(dataSet.axisDependency)
            mXBounds[mChart] = dataSet
            val circleRadius = dataSet.circleRadius
            val circleHoleRadius = dataSet.circleHoleRadius
            val drawCircleHole = dataSet.isDrawCircleHoleEnabled && circleHoleRadius < circleRadius && circleHoleRadius > 0f
            val drawTransparentCircleHole = drawCircleHole && dataSet.circleHoleColor == ColorTemplate.COLOR_NONE
            var imageCache: DataSetImageCache?
            if (mImageCaches.containsKey(dataSet)) {
                imageCache = mImageCaches[dataSet]
            } else {
                imageCache = DataSetImageCache()
                mImageCaches[dataSet] = imageCache
            }
            val changeRequired = imageCache!!.init(dataSet)

            if (changeRequired) { imageCache.fill(dataSet, drawCircleHole, drawTransparentCircleHole) }
            val boundsRangeCount = mXBounds.range + mXBounds.min
            for (j in mXBounds.min..boundsRangeCount) {
                val e = dataSet.getEntryForIndex(j) ?: break
                mCirclesBuffer[0] = e.x
                mCirclesBuffer[1] = e.y * phaseY
                trans.pointValuesToPixel(mCirclesBuffer)
                if (!mViewPortHandler.isInBoundsRight(mCirclesBuffer[0])) break
                if (!mViewPortHandler.isInBoundsLeft(mCirclesBuffer[0]) ||
                    !mViewPortHandler.isInBoundsY(mCirclesBuffer[1])
                ) continue
                val circleBitmap = imageCache.getBitmap(j)


                if (circleBitmap != null) {
                    c.drawBitmap(circleBitmap, mCirclesBuffer[0] - circleRadius, mCirclesBuffer[1] - circleRadius, null)
                }
            }
        }
    }
    
    override fun drawHighlighted(c: Canvas, indices: Array<Highlight>) {
        val lineData = mChart.lineData
        for (high in indices) {
            val set = lineData.getDataSetByIndex(high.dataSetIndex)
            if (set == null || !set.isHighlightEnabled) continue
            val e = set.getEntryForXValue(high.x, high.y)
            if (!isInBoundsX(e, set)) continue
            val pix = mChart.getTransformer(set.axisDependency).getPixelForValues(e.x, e.y * mAnimator.phaseY)
            high.setDraw(pix.x.toFloat(), pix.y.toFloat())

            drawHighlightLines(c, pix.x.toFloat(), pix.y.toFloat(), set)
        }
    }

    var bitmapConfig: Bitmap.Config
        get() = mBitmapConfig
        set(config) {
            mBitmapConfig = config
            releaseBitmap()
        }

    fun releaseBitmap() {
        if (mBitmapCanvas != null) {
            mBitmapCanvas!!.setBitmap(null)
            mBitmapCanvas = null
        }
        if (mDrawBitmap != null) {
            val drawBitmap = mDrawBitmap!!.get()
            drawBitmap?.recycle()
            mDrawBitmap!!.clear()
            mDrawBitmap = null
        }
    }

    private inner class DataSetImageCache {
        private val mCirclePathBuffer = Path()
        private var circleBitmaps: Array<Bitmap?>? = null

        fun init(set: ILineDataSet): Boolean {
            val size = set.circleColorCount
            var changeRequired = false
            if (circleBitmaps == null) {
                circleBitmaps = arrayOfNulls(size)
                changeRequired = true
            } else if (circleBitmaps!!.size != size) {
                circleBitmaps = arrayOfNulls(size)
                changeRequired = true
            }
            return changeRequired
        }

        fun fill(set: ILineDataSet, drawCircleHole: Boolean, drawTransparentCircleHole: Boolean) {
            val colorCount = set.circleColorCount
            val circleRadius = set.circleRadius
            val circleHoleRadius = set.circleHoleRadius

            for (i in 0 until colorCount) {
                val conf = Bitmap.Config.ARGB_4444
                val circleBitmap = Bitmap.createBitmap((circleRadius * 2.1).toInt(), (circleRadius * 2.1).toInt(), conf)
                val canvas = Canvas(circleBitmap)
                circleBitmaps!![i] = circleBitmap
                mRenderPaint.color = set.getCircleColor(i)
                if (drawTransparentCircleHole) {
                    mCirclePathBuffer.reset()
                    mCirclePathBuffer.addCircle(circleRadius, circleRadius, circleRadius, Path.Direction.CW)
                    mCirclePathBuffer.addCircle(circleRadius, circleRadius, circleHoleRadius, Path.Direction.CCW)
                    canvas.drawPath(mCirclePathBuffer, mRenderPaint)
                } else {
                    canvas.drawCircle(circleRadius, circleRadius, circleRadius, mRenderPaint)
                    if (drawCircleHole) {
                        canvas.drawCircle(circleRadius, circleRadius, circleHoleRadius, mCirclePaintInner)
                    }
                }
            }
        }

        fun getBitmap(index: Int): Bitmap? {
            return circleBitmaps!![index % circleBitmaps!!.size]
        }
    }
}
