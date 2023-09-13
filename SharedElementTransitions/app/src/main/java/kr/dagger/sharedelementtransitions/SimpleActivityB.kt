package kr.dagger.sharedelementtransitions

import android.os.Bundle
import androidx.activity.ComponentActivity
import kr.dagger.sharedelementtransitions.databinding.ActivitySimpleBBinding

class SimpleActivityB : ComponentActivity() {
	private val binding: ActivitySimpleBBinding by lazy {
		ActivitySimpleBBinding.inflate(
			layoutInflater
		)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding.lifecycleOwner = this@SimpleActivityB
		setContentView(binding.root)
	}
}