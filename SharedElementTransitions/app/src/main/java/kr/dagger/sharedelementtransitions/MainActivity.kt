package kr.dagger.sharedelementtransitions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import kr.dagger.sharedelementtransitions.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		binding.apply {
			lifecycleOwner = this@MainActivity
		}
		setContentView(binding.root)
		initView()
	}

	private fun initView() {
		binding.btnMoveActivity.setOnClickListener {
//			Intent(this@MainActivity, SimpleActivityA::class.java).run {
//				startActivity(this)
//			}
			Toast.makeText(this, "Not yet !!", Toast.LENGTH_LONG).show()
		}

		binding.btnMoveFragment.setOnClickListener {
			Intent(this@MainActivity, SimpleParentActivity::class.java).run {
				startActivity(this)
			}
		}
	}
}