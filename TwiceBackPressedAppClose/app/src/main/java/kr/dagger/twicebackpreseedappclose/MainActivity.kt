package kr.dagger.twicebackpreseedappclose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kr.dagger.twicebackpreseedappclose.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private lateinit var viewModel: MainViewModel

	private lateinit var binding: ActivityMainBinding

	companion object {
		private const val APP_FINISH_INTERVAL = 3_000L
	}

	private val backPressedCallback = object : OnBackPressedCallback(true) {
		override fun handleOnBackPressed() {
			viewModel.backPressedEvent()
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		viewModel = ViewModelProvider(this)[MainViewModel::class.java]

		onBackPressedDispatcher.addCallback(backPressedCallback)

		lifecycleScope.launch {
			lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.backPressedEvent
					.scan(listOf(System.currentTimeMillis() - APP_FINISH_INTERVAL)) { acc, _ ->
						acc.takeLast(1) + System.currentTimeMillis()
					}
					.drop(1)
					.collectLatest {
						if (it.last() - it.first() < APP_FINISH_INTERVAL) {
							finishAffinity()
						} else {
							// Toast message
//							Toast.makeText(
//								this@MainActivity,
//								"한 번 더 누르시면, 앱이 종료됩니다.",
//								Toast.LENGTH_SHORT
//							).show()

							// Snackbar message
							Snackbar.make(
								binding.root,
								"한 번 더 누르시면, 앱이 종료됩니다.",
								Snackbar.LENGTH_SHORT
							).show()
						}
					}
			}
		}
	}
}