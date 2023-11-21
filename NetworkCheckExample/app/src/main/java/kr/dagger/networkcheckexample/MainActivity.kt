package kr.dagger.networkcheckexample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.dagger.networkcheckexample.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private val snackBar: Snackbar by lazy { initSnackbar() }

	@Inject
	lateinit var networkMonitor: NetworkMonitor

	@SuppressLint("SetTextI18n")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		lifecycleScope.launch {
			lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
				networkMonitor.isOnline.collect { flag ->
					when (flag) {
						true -> {
							snackBar.dismiss()
							binding.tvAvailable.text = "Network Available!"
						}
						false -> {
							snackBar.show()
							binding.tvAvailable.text = "Network Invalid!"
						}
					}
				}
			}
		}
	}

	private fun initSnackbar(): Snackbar {
		return Snackbar.make(binding.root, "Network is lost", 10_000_0).apply {
			setAction("Close") {
				dismiss()
			}
			animationMode = Snackbar.ANIMATION_MODE_SLIDE
		}
	}
}