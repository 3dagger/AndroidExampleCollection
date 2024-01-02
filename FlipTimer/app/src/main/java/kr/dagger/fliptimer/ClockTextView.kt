package kr.dagger.fliptimer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

class ClockTextView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {
	private var alignment: TextAlignment = TextAlignment.TOP
	private val textRect = Rect()

	init {
		attrs?.let {
			context.obtainStyledAttributes(attrs, R.styleable.ClockTextView, defStyleAttr, 0)
				.use { typedArray ->
					val alignmentValue = typedArray.getInt(R.styleable.ClockTextView_alignment, 0)
					setAlignment(alignmentValue)
				}
		}
	}

	override fun onDraw(canvas: Canvas) {
		canvas.let {
			it.getClipBounds(textRect)
			val cHeight = textRect.height()
			paint.getTextBounds(this.text.toString(), 0, this.text.length, textRect)
			val bottom = textRect.bottom
			textRect.offset(-textRect.left, -textRect.top)
			paint.textAlign = Paint.Align.CENTER

			val drawY: Float = when (alignment) {
				TextAlignment.TOP -> (textRect.bottom - bottom).toFloat() - ((textRect.bottom - textRect.top) / 2)
				TextAlignment.BOTTOM -> top + cHeight.toFloat() + ((textRect.bottom - textRect.top) / 2)
			}

			val drawX = (width / 2).toFloat()
			paint.color = this.currentTextColor
			it.drawText(this.text.toString(), drawX, drawY, paint)
		}
	}

	private fun setAlignment(alignment: Int) {
		when (alignment) {
			1 -> this.alignment = TextAlignment.TOP
			2 -> this.alignment = TextAlignment.BOTTOM
		}
	}
}