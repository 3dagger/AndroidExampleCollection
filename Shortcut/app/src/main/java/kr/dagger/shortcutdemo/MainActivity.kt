package kr.dagger.shortcutdemo

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import kr.dagger.shortcutdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		WindowCompat.setDecorFitsSystemWindows(window, false)
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar)

		val navController = findNavController(R.id.nav_host_fragment_content_main)
		appBarConfiguration = AppBarConfiguration(navController.graph)
		setupActionBarWithNavController(navController, appBarConfiguration)

		binding.toolbar.setOnClickListener {
			Log.d("leeam", "toolbar clicked")
		}

		binding.fab.setOnClickListener { view ->
			createDynamicShortcut()
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.menu_main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return when (item.itemId) {
			R.id.action_settings -> true
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment_content_main)
		return navController.navigateUp(appBarConfiguration)
				|| super.onSupportNavigateUp()
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/07/19 11:27 오전
	 * @Description : Make pinned shortcut
	 * @History :
	 *
	 **/
	private fun createPinnedShortcut() {
		val sm = getSystemService(ShortcutManager::class.java)
		if (sm.isRequestPinShortcutSupported) {
			val pinShortcutInfo = ShortcutInfo.Builder(this, "manage_shortcuts").build()
			val pinnedShortcutCallbackIntent = sm.createShortcutResultIntent(pinShortcutInfo)
			val successCallback = PendingIntent.getBroadcast(this, 100, pinnedShortcutCallbackIntent, 0 or PendingIntent.FLAG_MUTABLE)
			sm.requestPinShortcut(pinShortcutInfo, successCallback.intentSender)
		}
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/07/19 11:50 오전
	 * @Description : Make dynamic shortcut
	 * @History :
	 *
	 **/
	private fun createDynamicShortcut() {
		val sm = getSystemService(ShortcutManager::class.java)
		val shortcut = ShortcutInfo.Builder(this, "TEST")
			.setShortLabel("WebSite")
			.setLongLabel("First Shortcut")
			.setDisabledMessage("This shortcut is disabled")
			.setIcon(Icon.createWithResource(this, R.drawable.ic_baseline_alarm_24))
			.setIntent(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com")))
			.build()

		val shortcut2 = ShortcutInfo.Builder(this, "TEST2")
			.setShortLabel("WebSite2")
			.setLongLabel("Second Shortcut")
			.setDisabledMessage("This shortcut is disabled")
			.setIcon(Icon.createWithResource(this, R.drawable.ic_baseline_alarm_24))
			.setIntent(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com")))
			.build()

		val shortcut3 = ShortcutInfo.Builder(this, "TEST3")
			.setShortLabel("WebSite3")
			.setLongLabel("Third Shortcut")
			.setDisabledMessage("This shortcut is disabled")
			.setIcon(Icon.createWithResource(this, R.drawable.ic_baseline_alarm_24))
			.setIntent(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com")))
			.build()

		sm.dynamicShortcuts = listOf(shortcut, shortcut2, shortcut3)
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/09/05 11:13 오전
	 * @Description : Disable Dynamic Shortcut
	 * @History : 
	 *
	 **/
	private fun disableDynamicShortcut() {
		val sm = getSystemService(ShortcutManager::class.java)
		sm.disableShortcuts(listOf("TEST", "TEST2"), "DISABLE")
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/09/05 11:13 오전
	 * @Description : Remove All Dynamic Shortcut
	 * @History : 
	 *
	 **/
	private fun removeAllDynamicShortcut() {
		val sm = getSystemService(ShortcutManager::class.java)
		sm.removeAllDynamicShortcuts()
	}
}