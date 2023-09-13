package kr.dagger.sharedelementtransitions

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import kr.dagger.sharedelementtransitions.databinding.ActivitySimpleParentBinding

class SimpleParentActivity : FragmentActivity() {
	private val binding: ActivitySimpleParentBinding by lazy {
		ActivitySimpleParentBinding.inflate(
			layoutInflater
		)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding.lifecycleOwner = this@SimpleParentActivity
		setContentView(binding.root)
		setUpNavigation()
	}

	private fun setUpNavigation() {
		val navHostFragment = NavHostFragment.create(R.navigation.simple_graph)
		supportFragmentManager.beginTransaction()
			.replace(R.id.content_layout, navHostFragment)
			.setPrimaryNavigationFragment(navHostFragment)
			.commit()
	}
}