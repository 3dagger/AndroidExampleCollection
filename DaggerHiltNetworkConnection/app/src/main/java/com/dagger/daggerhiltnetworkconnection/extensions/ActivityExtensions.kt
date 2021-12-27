package com.dagger.daggerhiltnetworkconnection.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun Context.toast(message: String?) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}