package kr.dagger.stateflowexample.presentation.dialog

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kr.dagger.stateflowexample.R
import kr.dagger.stateflowexample.base.BaseDialog

class SimpleDialogHelper(context: Context) : BaseDialog() {

    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.layout_dialog_network_error, null)
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogStyle)).setView(dialogView)

    val title: TextView by lazy {
        dialogView.findViewById(R.id.messageTextView)
    }

    val positiveButton: TextView by lazy {
        dialogView.findViewById(R.id.agreeTextView)
    }

    val negativeButton: TextView by lazy {
        dialogView.findViewById(R.id.disagreeTextView)
    }

    fun positiveClickListener(func: (() -> Unit)? = null) =
        with(positiveButton) {
            setClickListenerToDialogIcon(func)
        }

    fun negativeClickListener(func: (() -> Unit)? = null) =
        with(negativeButton) {
            setClickListenerToDialogIcon(func)
        }

    private fun View.setClickListenerToDialogIcon(func: (() -> Unit)?) =
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }
}