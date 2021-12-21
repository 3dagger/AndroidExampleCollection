package com.dagger.mpandroidchart.utility

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.dagger.mpandroidchart.R

class CustomProgress(context: Context): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.requestFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.custom_progress)
        setCancelable(false)
    }
}