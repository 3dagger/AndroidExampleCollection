package kr.dagger.sharedelementtransitions

import android.os.Bundle
import androidx.activity.ComponentActivity
import kr.dagger.sharedelementtransitions.databinding.ActivitySimpleABinding

class SimpleActivityA : ComponentActivity() {
	private val binding: ActivitySimpleABinding by lazy {
		ActivitySimpleABinding.inflate(
			layoutInflater
		)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding.apply {
			lifecycleOwner = this@SimpleActivityA
		}
		setContentView(binding.root)
	}
}