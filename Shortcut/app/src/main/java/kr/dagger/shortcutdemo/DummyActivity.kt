package kr.dagger.shortcutdemo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kr.dagger.shortcutdemo.databinding.ActivityDummyBinding

class DummyActivity : AppCompatActivity() {

	private lateinit var binding: ActivityDummyBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDummyBinding.inflate(layoutInflater)

	}
}