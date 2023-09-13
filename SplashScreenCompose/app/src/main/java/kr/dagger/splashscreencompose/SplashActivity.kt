package kr.dagger.splashscreencompose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		Log.d("leeam", "VersionCode S :: ${Build.VERSION_CODES.S}\nSDK_INT :: ${Build.VERSION.SDK_INT}")
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			Log.d("leeam", "SDK S Over")
			val splashScreen = installSplashScreen()
			splashScreen.setKeepOnScreenCondition { true }
		}
		super.onCreate(savedInstanceState)
		lifecycleScope.launch {
			delay(3000)
			val intent = Intent(this@SplashActivity, MainActivity::class.java)
			startActivity(intent)
			finish()
		}
	}
}