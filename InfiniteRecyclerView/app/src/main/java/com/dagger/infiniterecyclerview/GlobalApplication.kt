package com.dagger.infiniterecyclerview

import android.app.Application
import android.content.ContextWrapper
import com.dagger.infiniterecyclerview.di.apiModules
import com.dagger.infiniterecyclerview.di.networkModules
import com.dagger.infiniterecyclerview.di.viewModelModules
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.pixplicity.easyprefs.library.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GlobalApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GlobalApplication)
            modules(listOf(apiModules, networkModules, viewModelModules))
        }

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

        // init Easy Pref
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}