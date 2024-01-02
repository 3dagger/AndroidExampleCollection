package kr.dagger.fliptimer

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import kr.dagger.fliptimer.databinding.ViewCountDownDigitBinding

class CountDownDigitView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {
	private var animationDuration = 500L
	private var binding: ViewCountDownDigitBinding

	init {
		binding = ViewCountDownDigitBinding.inflate(LayoutInflater.from(context), this, true)
		binding.apply {
			frontUpperText.measure(0, 0)
			frontLowerText.measure(0, 0)
			backUpperText.measure(0, 0)
			backLowerText.measure(0, 0)
		}
	}

	fun setNewText(newText: String) {
		binding.frontUpper.clearAnimation()
		binding.frontLower.clearAnimation()

		binding.frontUpperText.text = newText
		binding.frontLowerText.text = newText
		binding.backUpperText.text = newText
		binding.backLowerText.text = newText
	}


	fun getFrontUpper(): FrameLayout {
		return binding.frontUpper
	}

	fun getFrontLower(): FrameLayout {
		return binding.frontLower
	}

	fun getBackUpper(): FrameLayout {
		return binding.backUpper
	}

	fun getBackLower(): FrameLayout {
		return binding.backLower
	}

	fun getFrontUpperText(): TextView {
		return binding.frontUpperText
	}

	fun getFrontLowerText(): TextView {
		return binding.frontLowerText
	}

	fun getBackUpperText(): TextView {
		return binding.backUpperText
	}

	fun getBackLowerText(): TextView {
		return binding.backLowerText
	}

	fun getDigitDivider(): View {
		return binding.digitDivider
	}

	fun animateTextChange(newText: String) {
		if (binding.backUpperText.text == newText) {
			return
		}

		binding.frontUpper.clearAnimation()
		binding.frontLower.clearAnimation()

		binding.backUpperText.text = newText
		binding.frontUpper.pivotY = binding.frontUpper.bottom.toFloat()
		binding.frontLower.pivotY = binding.frontUpper.top.toFloat()
		binding.frontUpper.pivotX = (binding.frontUpper.right - ((binding.frontUpper.right - binding.frontUpper.left) / 2)).toFloat()
		binding.frontLower.pivotX = (binding.frontUpper.right - ((binding.frontUpper.right - binding.frontUpper.left) / 2)).toFloat()

		binding.frontUpper.animate().setDuration(getHalfOfAnimationDuration()).rotationX(-90f).setInterpolator(
			AccelerateInterpolator()
		)
			.withEndAction {
				binding.frontUpperText.text = binding.backUpperText.text
				binding.frontUpper.rotationX = 0f
				binding.frontLower.rotationX = 90f
				binding.frontLowerText.text = binding.backUpperText.text
				binding.frontLower.animate().setDuration(getHalfOfAnimationDuration()).rotationX(0f).setInterpolator(
					DecelerateInterpolator()
				)
					.withEndAction {
						binding.backLowerText.text = binding.frontLowerText.text
					}.start()
			}.start()
	}

	fun setAnimationDuration(duration: Long) {
		this.animationDuration = duration
	}

	fun setTypeFace(typeFace: Typeface) = with(binding) {
		frontUpperText.typeface = typeFace
		frontLowerText.typeface = typeFace
		backUpperText.typeface = typeFace
		backLowerText.typeface = typeFace
	}

	private fun getHalfOfAnimationDuration(): Long {
		return animationDuration / 2
	}
}