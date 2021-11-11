package com.dagger.custombottomsheetbehavior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class MainActivity : AppCompatActivity() {
    private lateinit var transAction        : FragmentTransaction
    private val fragmentManager             : FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFragment = supportFragmentManager.findFragmentById(R.id.mainFrameLayout) as MainFragment?
            ?: MainFragment()
        transAction = fragmentManager.beginTransaction()
        transAction.replace(R.id.mainFrameLayout, mFragment).commitNow()

        initLogger()
    }

    private fun initLogger() {
        // init Logger
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true) // 쓰레드 보여줄 것인지
            .methodCount(2)        // 몇라인까지 출력할지, 기본값 2
            .methodOffset(5)       // 메서드 오프셋, 기본값 5
            .tag("leeam")      // 글로벌 태그
            .build()

        // 디버그에서만 로그출력
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}

