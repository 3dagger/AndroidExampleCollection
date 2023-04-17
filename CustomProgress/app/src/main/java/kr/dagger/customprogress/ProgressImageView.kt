package kr.dagger.customprogress

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.dagger.customprogress.databinding.LayoutCustomProgressBinding


class ProgressImageView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null
): ConstraintLayout(context, attrs) {

	private val binding: LayoutCustomProgressBinding by lazy {
		LayoutCustomProgressBinding.bind(
			LayoutInflater.from(context).inflate(R.layout.layout_custom_progress, this, false)
		)
	}

	init {
		initView()
	}

	private fun initView() {
		binding.apply {
			progress.setBackgroundResource(R.drawable.common_loading_progress)
			post { (progress.background as AnimationDrawable).start() }
			progressTxt.text = getProgressText()
			addView(root)
		}
	}

	fun showProgress() {
		if (!binding.root.isVisible) binding.root.visibility = View.VISIBLE
	}

	fun dismissProgress() {
		if (binding.root.isVisible) binding.root.visibility = View.GONE
	}

	private fun getProgressText(): String {
		return listOf(
			context.getString(R.string.loading_text01),
			context.getString(R.string.loading_text02),
			context.getString(R.string.loading_text03),
		).shuffled().first()
	}
}