package com.dagger.mpandroidchart.base

import android.os.Handler
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

open class BaseViewModel<N> : ViewModel() {
    private lateinit var mNavigator : WeakReference<N>
    private lateinit var handler    : Handler

    private val compositeDisposable = CompositeDisposable()

    fun getNavigator(): N {
        return mNavigator.get()!!
    }

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }

    fun getHandler(): Handler {
        return handler
    }

    fun setHandler(handler: Handler) {
        this.handler = handler
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}