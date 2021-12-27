package com.dagger.daggerhiltnetworkconnection.binding

import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.databinding.BindingAdapter

object ViewBinding {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        if(!text.isNullOrEmpty()) {
            Toast.makeText(view.context, text, Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(view.context, "널임", Toast.LENGTH_SHORT).show()
        }
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if(shouldBeGone) {
            View.GONE
        }else {
            View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun bindUrl(webView: WebView, url: String?) {
        url?.let { webView.loadUrl(it) }
    }
}