package com.dagger.mpandroidchart.ext

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dagger.mpandroidchart.R
import com.orhanobut.logger.Logger

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

@BindingAdapter("bindJudgementCount")
fun TextView.bindUserBatteryUsageText(value : Int) {
    when {
        value > 0 -> {
            this.setTextColor(Color.parseColor("#ff1e00"))
            this.text = value.toString()
        }
        value < 0 -> {
            this.setTextColor(Color.parseColor("#2cafdd"))
            this.text = (value * -1).toString()
        }
        else -> {
            this.setTextColor(Color.parseColor("#000000"))
            this.text = value.toString()
        }
    }
}

@BindingAdapter("bindUserBatteryUsageArrowImage")
fun ImageView.bindUserBatteryUsageArrowImage(value: Int) {
    when {
        value > 0 -> this.setImageResource(R.drawable.ic_icon_up)
        value < 0 -> this.setImageResource(R.drawable.ic_icon_down)
        else -> this.setImageResource(R.drawable.ic_baseline_horizontal_rule_24)
    }
}