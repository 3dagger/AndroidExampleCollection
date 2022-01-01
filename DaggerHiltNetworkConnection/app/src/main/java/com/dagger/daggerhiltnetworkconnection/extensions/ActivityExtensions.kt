package com.dagger.daggerhiltnetworkconnection.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.orhanobut.logger.Logger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun Context.toast(message: String?) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun EditText.textChange(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {

        val listener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun afterTextChanged(s: Editable?) = Unit
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(text)
            }
        }

        addTextChangedListener(listener)

        awaitClose {
            Logger.d("awaitClose 실행")
            removeTextChangedListener(listener)
        }
    }.onStart {  emit(text) }
}