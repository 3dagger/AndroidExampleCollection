package kr.dagger.fliptimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import kr.dagger.fliptimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private var isRunning: Boolean = true

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val typeFace = ResourcesCompat.getFont(this, R.font.roboto_bold)
		binding.apply {
			countDownView.setCustomTypeface(typeFace!!)
			countDownView.startCountDown(99999999)
			countDownView.setCountdownListener(object : CountDownCallback {
				override fun countdownAboutToFinish() {
					Log.d("leeam", "countdownAboutToFinish")
				}

				override fun countdownFinished() {
					Log.d("leeam", "countDownFinished")
					Toast.makeText(this@MainActivity, "Finished", Toast.LENGTH_SHORT).show()
					countDownView.resetCountdownTimer()
					isRunning = false
					btn.isEnabled = false
				}
			})

			btn.setOnClickListener {
				if (isRunning) {
					btn.text = "RESUME"
					countDownView.pauseCountDownTimer()
					isRunning = false
				} else {
					btn.text = "STOP"
					countDownView.resumeCountDownTimer()
				}
			}
		}
	}
}