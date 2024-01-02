package kr.dagger.fliptimer

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import kr.dagger.fliptimer.databinding.ViewCountDownBinding
import java.util.concurrent.TimeUnit

class CountDownView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
	private var binding: ViewCountDownBinding
	private var countDownTimer: CountDownTimer? = null
	private var countdownListener: CountDownCallback? = null
	private var countdownTickInterval = 1000
	private var almostFinishedCallbackTimeInSeconds: Int = 5
	private var resetSymbol: String = "8"
	private var milliLeft: Long = 0

	init {
		Log.d("leeam", "CountDownView init")
		binding = ViewCountDownBinding.inflate(LayoutInflater.from(context), this, true)

		attrs?.let {
			val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView, defStyleAttr, 0)
			val resetSymbol = typedArray.getString(R.styleable.CountDownView_resetSymbol)
			if (resetSymbol != null) {
				setResetSymbol(resetSymbol)
			}

			val digitTopDrawable = typedArray.getDrawable(R.styleable.CountDownView_digitTopDrawable)
			setDigitTopDrawable(digitTopDrawable)
			val digitBottomDrawable = typedArray.getDrawable(R.styleable.CountDownView_digitBottomDrawable)
			setDigitBottomDrawable(digitBottomDrawable)
			val digitDividerColor = typedArray.getColor(R.styleable.CountDownView_digitDividerColor, 0)
			setDigitDividerColor(digitDividerColor)
			val digitSplitterColor = typedArray.getColor(R.styleable.CountDownView_digitSplitterColor, 0)
			setDigitSplitterColor(digitSplitterColor)
			val digitTextColor = typedArray.getColor(R.styleable.CountDownView_digitTextColor, 0)
			setDigitTextColor(digitTextColor)
			val digitTextSize = typedArray.getDimension(R.styleable.CountDownView_digitTextSize, 0f)
			setDigitTextSize(digitTextSize)
			setSplitterDigitTextSize(digitTextSize)
			val digitPadding = typedArray.getDimension(R.styleable.CountDownView_digitPadding, 0f)
			setDigitPadding(digitPadding.toInt())
			val splitterPadding = typedArray.getDimension(R.styleable.CountDownView_splitterPadding, 0f)
			setSplitterPadding(splitterPadding.toInt())
			val halfDigitHeight = typedArray.getDimensionPixelSize(R.styleable.CountDownView_halfDigitHeight, 0)
			val digitWidth = typedArray.getDimensionPixelSize(R.styleable.CountDownView_digitWidth, 0)
			setHalfDigitHeightAndDigitWidth(halfDigitHeight, digitWidth)
			val animationDuration = typedArray.getInt(R.styleable.CountDownView_animationDuration, 0)
			setAnimationDuration(animationDuration)
			val almostFinishedCallbackTimeInSeconds = typedArray.getInt(R.styleable.CountDownView_almostFinishedCallbackTimeInSeconds, 5)
			setAlmostFinishedCallbackTimeInSeconds(almostFinishedCallbackTimeInSeconds)
			val countdownTickInterval = typedArray.getInt(R.styleable.CountDownView_countdownTickInterval, 1000)
			this.countdownTickInterval = countdownTickInterval

			invalidate()
			typedArray.recycle()
		}
	}

	fun startCountDown(timeToNextEvent: Long) {
		countDownTimer?.cancel()
		var hasCalledAlmostFinished = false
		countDownTimer = object : CountDownTimer(timeToNextEvent, countdownTickInterval.toLong()) {
			override fun onTick(millisUntilFinished: Long) {
				milliLeft = millisUntilFinished
				if (millisUntilFinished / 1000 <= almostFinishedCallbackTimeInSeconds && !hasCalledAlmostFinished) {
					hasCalledAlmostFinished = true
					countdownListener?.countdownAboutToFinish()
				}
				setCountDownTime(millisUntilFinished)
			}

			override fun onFinish() {
				hasCalledAlmostFinished = false
				countdownListener?.countdownFinished()
			}
		}
		countDownTimer?.start()
	}

	fun resetCountdownTimer() = with(binding) {
		countDownTimer?.cancel()
		firstDigitDays.setNewText(resetSymbol)
		secondDigitDays.setNewText(resetSymbol)
		firstDigitHours.setNewText(resetSymbol)
		secondDigitHours.setNewText(resetSymbol)
		firstDigitMinute.setNewText(resetSymbol)
		secondDigitMinute.setNewText(resetSymbol)
		firstDigitSecond.setNewText(resetSymbol)
		secondDigitSecond.setNewText(resetSymbol)
	}

	private fun setCountDownTime(timeToStart: Long) = with(binding) {
		val days = TimeUnit.MILLISECONDS.toDays(timeToStart)
		val hours = TimeUnit.MILLISECONDS.toHours(timeToStart - TimeUnit.DAYS.toMillis(days))
		val minutes = TimeUnit.MILLISECONDS.toMinutes(timeToStart - (TimeUnit.DAYS.toMillis(days) + TimeUnit.HOURS.toMillis(hours)))
		val seconds = TimeUnit.MILLISECONDS.toSeconds(
			timeToStart - (TimeUnit.DAYS.toMillis(days) + TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(
				minutes
			))
		)

		val daysString = days.toString()
		val hoursString = hours.toString()
		val minutesString = minutes.toString()
		val secondsString = seconds.toString()


		when (daysString.length) {
			2 -> {
				firstDigitDays.animateTextChange((daysString[0].toString()))
				secondDigitDays.animateTextChange((daysString[1].toString()))
			}
			1 -> {
				firstDigitDays.animateTextChange(("0"))
				secondDigitDays.animateTextChange((daysString[0].toString()))
			}
			else -> {
				firstDigitDays.animateTextChange(("3"))
				secondDigitDays.animateTextChange(("0"))
			}
		}

		when (hoursString.length) {
			2 -> {
				firstDigitHours.animateTextChange((hoursString[0].toString()))
				secondDigitHours.animateTextChange((hoursString[1].toString()))
			}
			1 -> {
				firstDigitHours.animateTextChange(("0"))
				secondDigitHours.animateTextChange((hoursString[0].toString()))
			}
			else -> {
				firstDigitHours.animateTextChange(("1"))
				secondDigitHours.animateTextChange(("1"))
			}
		}

		when (minutesString.length) {
			2 -> {
				firstDigitMinute.animateTextChange((minutesString[0].toString()))
				secondDigitMinute.animateTextChange((minutesString[1].toString()))
			}
			1 -> {
				firstDigitMinute.animateTextChange(("0"))
				secondDigitMinute.animateTextChange((minutesString[0].toString()))
			}
			else -> {
				firstDigitMinute.animateTextChange(("5"))
				secondDigitMinute.animateTextChange(("9"))
			}
		}
		when (secondsString.length) {
			2 -> {
				firstDigitSecond.animateTextChange((secondsString[0].toString()))
				secondDigitSecond.animateTextChange((secondsString[1].toString()))
			}
			1 -> {
				firstDigitSecond.animateTextChange(("0"))
				secondDigitSecond.animateTextChange((secondsString[0].toString()))
			}
			else -> {
				firstDigitSecond.animateTextChange((secondsString[secondsString.length - 2].toString()))
				secondDigitSecond.animateTextChange((secondsString[secondsString.length - 1].toString()))
			}
		}
	}

	private fun setResetSymbol(resetSymbol: String?) {
		resetSymbol?.let {
			if (it.isNotEmpty()) {
				this.resetSymbol = resetSymbol
			} else {
				this.resetSymbol = ""
			}
		} ?: kotlin.run {
			this.resetSymbol = ""
		}
	}

	private fun setDigitTopDrawable(digitTopDrawable: Drawable?) = with(binding) {
		if (digitTopDrawable != null) {
			firstDigitDays.getFrontUpper().background = digitTopDrawable
			firstDigitDays.getBackUpper().background = digitTopDrawable
			secondDigitDays.getFrontUpper().background = digitTopDrawable
			secondDigitDays.getBackUpper().background = digitTopDrawable
			firstDigitHours.getFrontUpper().background = digitTopDrawable
			firstDigitHours.getBackUpper().background = digitTopDrawable
			secondDigitHours.getFrontUpper().background = digitTopDrawable
			secondDigitHours.getBackUpper().background = digitTopDrawable
			firstDigitMinute.getFrontUpper().background = digitTopDrawable
			firstDigitMinute.getBackUpper().background = digitTopDrawable
			secondDigitMinute.getFrontUpper().background = digitTopDrawable
			secondDigitMinute.getBackUpper().background = digitTopDrawable
			firstDigitSecond.getFrontUpper().background = digitTopDrawable
			firstDigitSecond.getBackUpper().background = digitTopDrawable
			secondDigitSecond.getFrontUpper().background = digitTopDrawable
			secondDigitSecond.getBackUpper().background = digitTopDrawable
		} else {
			setTransparentBackgroundColor()
		}
	}

	private fun setDigitBottomDrawable(digitBottomDrawable: Drawable?) = with(binding) {
		if (digitBottomDrawable != null) {
			firstDigitDays.getFrontLower().background = digitBottomDrawable
			firstDigitDays.getBackLower().background = digitBottomDrawable
			secondDigitDays.getFrontLower().background = digitBottomDrawable
			secondDigitDays.getBackLower().background = digitBottomDrawable
			firstDigitHours.getFrontLower().background = digitBottomDrawable
			firstDigitHours.getBackLower().background = digitBottomDrawable
			secondDigitHours.getFrontLower().background = digitBottomDrawable
			secondDigitHours.getBackLower().background = digitBottomDrawable
			firstDigitMinute.getFrontLower().background = digitBottomDrawable
			firstDigitMinute.getBackLower().background = digitBottomDrawable
			secondDigitMinute.getFrontLower().background = digitBottomDrawable
			secondDigitMinute.getBackLower().background = digitBottomDrawable
			firstDigitSecond.getFrontLower().background = digitBottomDrawable
			firstDigitSecond.getBackLower().background = digitBottomDrawable
			secondDigitSecond.getFrontLower().background = digitBottomDrawable
			secondDigitSecond.getBackLower().background = digitBottomDrawable
		} else {
			setTransparentBackgroundColor()
		}
	}

	private fun setDigitDividerColor(digitDividerColor: Int) = with(binding) {
		var dividerColor = digitDividerColor
		if (dividerColor == 0) {
			dividerColor = ContextCompat.getColor(context, R.color.transparent)
		}

		firstDigitDays.getDigitDivider().setBackgroundColor(dividerColor)
		secondDigitDays.getDigitDivider().setBackgroundColor(dividerColor)
		firstDigitHours.getDigitDivider().setBackgroundColor(dividerColor)
		secondDigitHours.getDigitDivider().setBackgroundColor(dividerColor)
		firstDigitMinute.getDigitDivider().setBackgroundColor(dividerColor)
		secondDigitMinute.getDigitDivider().setBackgroundColor(dividerColor)
		firstDigitSecond.getDigitDivider().setBackgroundColor(dividerColor)
		secondDigitSecond.getDigitDivider().setBackgroundColor(dividerColor)
	}

	private fun setDigitSplitterColor(digitsSplitterColor: Int) {
		if (digitsSplitterColor != 0) {
			//  digitsSplitter.setTextColor(digitsSplitterColor)
		} else {
			// digitsSplitter.setTextColor(ContextCompat.getColor(context, R.color.transparent))
		}
	}

	private fun setSplitterDigitTextSize(digitsTextSize: Float) {
		//digitsSplitter.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
	}

	private fun setDigitPadding(digitPadding: Int) = with(binding) {

		firstDigitDays.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
		secondDigitDays.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
		firstDigitHours.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
		secondDigitHours.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)

		firstDigitMinute.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
		secondDigitMinute.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
		firstDigitSecond.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
		secondDigitSecond.setPadding(digitPadding, digitPadding, digitPadding, digitPadding)
	}

	private fun setSplitterPadding(splitterPadding: Int) {
		//digitsSplitter.setPadding(splitterPadding, 0, splitterPadding, 0)
	}

	private fun setDigitTextColor(digitsTextColor: Int) = with(binding) {
		var textColor = digitsTextColor
		if (textColor == 0) {
			textColor = ContextCompat.getColor(context, R.color.transparent)
		}

		firstDigitDays.getFrontUpperText().setTextColor(textColor)
		firstDigitDays.getBackUpperText().setTextColor(textColor)
		firstDigitHours.getFrontUpperText().setTextColor(textColor)
		firstDigitHours.getBackUpperText().setTextColor(textColor)
		secondDigitDays.getFrontUpperText().setTextColor(textColor)
		secondDigitDays.getBackUpperText().setTextColor(textColor)
		secondDigitHours.getFrontUpperText().setTextColor(textColor)
		secondDigitHours.getBackUpperText().setTextColor(textColor)

		firstDigitMinute.getFrontUpperText().setTextColor(textColor)
		firstDigitMinute.getBackUpperText().setTextColor(textColor)
		secondDigitMinute.getFrontUpperText().setTextColor(textColor)
		secondDigitMinute.getBackUpperText().setTextColor(textColor)
		firstDigitSecond.getFrontUpperText().setTextColor(textColor)
		firstDigitSecond.getBackUpperText().setTextColor(textColor)
		secondDigitSecond.getFrontUpperText().setTextColor(textColor)
		secondDigitSecond.getBackUpperText().setTextColor(textColor)


		firstDigitDays.getFrontLowerText().setTextColor(textColor)
		firstDigitDays.getBackLowerText().setTextColor(textColor)

		firstDigitHours.getFrontLowerText().setTextColor(textColor)
		firstDigitHours.getBackLowerText().setTextColor(textColor)

		secondDigitDays.getFrontLowerText().setTextColor(textColor)
		secondDigitDays.getBackLowerText().setTextColor(textColor)

		secondDigitHours.getFrontLowerText().setTextColor(textColor)
		secondDigitHours.getBackLowerText().setTextColor(textColor)

		firstDigitMinute.getFrontLowerText().setTextColor(textColor)
		firstDigitMinute.getBackLowerText().setTextColor(textColor)
		secondDigitMinute.getFrontLowerText().setTextColor(textColor)
		secondDigitMinute.getBackLowerText().setTextColor(textColor)
		firstDigitSecond.getFrontLowerText().setTextColor(textColor)
		firstDigitSecond.getBackLowerText().setTextColor(textColor)
		secondDigitSecond.getFrontLowerText().setTextColor(textColor)
		secondDigitSecond.getBackLowerText().setTextColor(textColor)
	}

	private fun setDigitTextSize(digitsTextSize: Float) = with(binding) {
		firstDigitDays.getFrontUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitDays.getBackUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitDays.getFrontUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitDays.getBackUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitHours.getFrontUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitHours.getBackUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitHours.getFrontUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitHours.getBackUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitMinute.getFrontUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitMinute.getBackUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitMinute.getFrontUpperText()
			.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitMinute.getBackUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitSecond.getFrontUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitSecond.getBackUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitSecond.getFrontUpperText()
			.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitSecond.getBackUpperText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitDays.getFrontLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitDays.getBackLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitDays.getFrontLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitDays.getBackLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitHours.getFrontLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitHours.getBackLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitHours.getFrontLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitHours.getBackLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitMinute.getFrontLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitMinute.getBackLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitMinute.getFrontLowerText()
			.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitMinute.getBackLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitSecond.getFrontLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		firstDigitSecond.getBackLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitSecond.getFrontLowerText()
			.setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
		secondDigitSecond.getBackLowerText().setTextSize(TypedValue.COMPLEX_UNIT_PX, digitsTextSize)
	}

	private fun setHalfDigitHeightAndDigitWidth(halfDigitHeight: Int, digitWidth: Int) =
		with(binding) {
			setHeightAndWidthToView(firstDigitDays.getFrontUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitDays.getBackUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitDays.getFrontUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitDays.getBackUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitHours.getFrontUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitHours.getBackUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitHours.getFrontUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitHours.getBackUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitMinute.getFrontUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitMinute.getBackUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitMinute.getFrontUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitMinute.getBackUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitSecond.getFrontUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitSecond.getBackUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitSecond.getFrontUpper(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitSecond.getBackUpper(), halfDigitHeight, digitWidth)

			// Lower
			setHeightAndWidthToView(firstDigitDays.getFrontLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitDays.getBackLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitDays.getFrontLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitDays.getBackLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitHours.getFrontLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitHours.getBackLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitHours.getFrontLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitHours.getBackLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitMinute.getFrontLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitMinute.getBackLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitMinute.getFrontLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitMinute.getBackLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitSecond.getFrontLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(firstDigitSecond.getBackLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitSecond.getFrontLower(), halfDigitHeight, digitWidth)
			setHeightAndWidthToView(secondDigitSecond.getBackLower(), halfDigitHeight, digitWidth)

			// Dividers
			firstDigitDays.getDigitDivider().layoutParams.width = digitWidth
			secondDigitDays.getDigitDivider().layoutParams.width = digitWidth
			firstDigitHours.getDigitDivider().layoutParams.width = digitWidth
			secondDigitHours.getDigitDivider().layoutParams.width = digitWidth
			firstDigitMinute.getDigitDivider().layoutParams.width = digitWidth
			secondDigitMinute.getDigitDivider().layoutParams.width = digitWidth
			firstDigitSecond.getDigitDivider().layoutParams.width = digitWidth
			secondDigitSecond.getDigitDivider().layoutParams.width = digitWidth
		}

	private fun setHeightAndWidthToView(view: View, halfDigitHeight: Int, digitWidth: Int) =
		with(binding) {
			val firstDigitMinuteFrontUpperLayoutParams = view.layoutParams
			firstDigitMinuteFrontUpperLayoutParams.height = halfDigitHeight
			firstDigitMinuteFrontUpperLayoutParams.width = digitWidth
			firstDigitDays.getFrontUpper().layoutParams = firstDigitMinuteFrontUpperLayoutParams
		}

	private fun setAnimationDuration(animationDuration: Int) = with(binding) {
		firstDigitDays.setAnimationDuration(animationDuration.toLong())
		secondDigitDays.setAnimationDuration(animationDuration.toLong())
		firstDigitHours.setAnimationDuration(animationDuration.toLong())
		secondDigitHours.setAnimationDuration(animationDuration.toLong())
		firstDigitMinute.setAnimationDuration(animationDuration.toLong())
		secondDigitMinute.setAnimationDuration(animationDuration.toLong())
		firstDigitSecond.setAnimationDuration(animationDuration.toLong())
		secondDigitSecond.setAnimationDuration(animationDuration.toLong())
	}

	private fun setAlmostFinishedCallbackTimeInSeconds(almostFinishedCallbackTimeInSeconds: Int) {
		this.almostFinishedCallbackTimeInSeconds = almostFinishedCallbackTimeInSeconds
	}

	private fun setTransparentBackgroundColor() = with(binding) {
		val transparent = ContextCompat.getColor(context, R.color.transparent)
		firstDigitDays.getFrontLower().setBackgroundColor(transparent)
		firstDigitDays.getBackLower().setBackgroundColor(transparent)
		secondDigitDays.getFrontLower().setBackgroundColor(transparent)
		secondDigitDays.getBackLower().setBackgroundColor(transparent)
		firstDigitHours.getFrontLower().setBackgroundColor(transparent)
		firstDigitHours.getBackLower().setBackgroundColor(transparent)
		secondDigitHours.getFrontLower().setBackgroundColor(transparent)
		secondDigitHours.getBackLower().setBackgroundColor(transparent)
		firstDigitMinute.getFrontLower().setBackgroundColor(transparent)
		firstDigitMinute.getBackLower().setBackgroundColor(transparent)
		secondDigitMinute.getFrontLower().setBackgroundColor(transparent)
		secondDigitMinute.getBackLower().setBackgroundColor(transparent)
		firstDigitSecond.getFrontLower().setBackgroundColor(transparent)
		firstDigitSecond.getBackLower().setBackgroundColor(transparent)
		secondDigitSecond.getFrontLower().setBackgroundColor(transparent)
		secondDigitSecond.getBackLower().setBackgroundColor(transparent)
	}

	fun setCountdownListener(countdownListener: CountDownCallback) {
		this.countdownListener = countdownListener
	}

	fun pauseCountDownTimer() {
		countDownTimer?.cancel()
	}

	fun resumeCountDownTimer() {
		startCountDown(milliLeft)
	}

	fun setCustomTypeface(typeface: Typeface) = with(binding) {
		firstDigitDays.setTypeFace(typeface)
		firstDigitDays.setTypeFace(typeface)
		secondDigitDays.setTypeFace(typeface)
		secondDigitDays.setTypeFace(typeface)
		firstDigitHours.setTypeFace(typeface)
		firstDigitHours.setTypeFace(typeface)
		secondDigitHours.setTypeFace(typeface)
		secondDigitHours.setTypeFace(typeface)
		firstDigitMinute.setTypeFace(typeface)
		firstDigitMinute.setTypeFace(typeface)
		secondDigitMinute.setTypeFace(typeface)
		secondDigitMinute.setTypeFace(typeface)
		firstDigitSecond.setTypeFace(typeface)
		firstDigitSecond.setTypeFace(typeface)
		secondDigitSecond.setTypeFace(typeface)
		secondDigitSecond.setTypeFace(typeface)
	}
}