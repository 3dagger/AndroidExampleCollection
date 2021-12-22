package com.dagger.daggerhiltnetworkconnection

import android.app.Application
import com.dagger.daggerhiltnetworkconnection.Constants.Companion.TAG_NAME
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // init Logger
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true) // 쓰레드 보여줄 것인지
            .methodCount(2)        // 몇라인까지 출력할지, 기본값 2
            .methodOffset(5)       // 메서드 오프셋, 기본값 5
            .tag(TAG_NAME)      // 글로벌 태그
            .build()

        // 디버그에서만 로그출력
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}