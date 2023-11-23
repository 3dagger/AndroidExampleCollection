package kr.dagger.chromecustomtabsexample

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import kr.dagger.chromecustomtabsexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding

	private val customTabsIntent: CustomTabsIntent by lazy {
		CustomTabsIntent
			.Builder()
			.build()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		initClickListener()
	}

	private fun initClickListener() = with(binding) {
		val url1 = "https://www.0404.go.kr/dev/main.mofa"
		val url2 = "https://tketlink.dn.toastoven.net/tl/event/akmutopia(6)/event_m.html"
		val url3 = "https://www.google.com"
		val url4 = "https://www.drtour.com"

		btnFirst.setOnClickListener {
			CustomTabsIntent
				.Builder()
				.build()
				.run {
					launchUrl(this@MainActivity, Uri.parse(url1))
				}
		}

		btnSecond.setOnClickListener {
			CustomTabsIntent
				.Builder()
				.setStartAnimations(this@MainActivity, R.anim.slide_left, R.anim.wait_anim)
				.setExitAnimations(this@MainActivity,R.anim.wait_anim, R.anim.slide_right)
				.build()
				.run {
					launchUrl(this@MainActivity, Uri.parse(url2))
				}
		}

		btnThird.setOnClickListener {
			CustomTabsIntent
				.Builder()
				.setUrlBarHidingEnabled(true)
				.build()
				.run {
					launchUrl(this@MainActivity, Uri.parse(url3))
				}

		}

		btnFourth.setOnClickListener {
			CustomTabsIntent
				.Builder()
				.setShowTitle(true)
				.build()
				.run {
					launchUrl(this@MainActivity, Uri.parse(url4))
				}
		}
	}

//	private fun createCustomTabsIntent(): CustomTabsIntent =
//		CustomTabsIntent.Builder()
//			.build()

//	private fun launchCustomTabs(customTabsIntent: CustomTabsIntent, url: String) =
//		customTabsIntent.launchUrl(this@MainActivity, Uri.parse(url))
}