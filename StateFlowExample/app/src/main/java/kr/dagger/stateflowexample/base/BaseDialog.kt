package kr.dagger.stateflowexample.base


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.AlertDialog

abstract class BaseDialog {

    abstract val dialogView: View
    abstract val builder: AlertDialog.Builder

    open var cancelable: Boolean = true
    open var isBackGroundTransparent: Boolean = true
    open var dialog: AlertDialog? = null

    open fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        if (isBackGroundTransparent)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog!!
    }
}
