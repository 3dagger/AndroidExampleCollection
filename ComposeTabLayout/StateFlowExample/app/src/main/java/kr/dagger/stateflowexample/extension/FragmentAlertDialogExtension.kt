package kr.dagger.stateflowexample.extension

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.tourbaksaapp.app.common.extension.DialogNegativeCallback
import com.tourbaksaapp.app.common.extension.DialogPositiveCallback
import kr.dagger.stateflowexample.presentation.dialog.SimpleDialogHelper

fun Fragment.showSimpleDialog(message: String): AlertDialog =
	SimpleDialogHelper(requireContext()).apply {
		cancelable = false
		title.text = message
		positiveButton.visibility = View.VISIBLE
		negativeButton.visibility = View.VISIBLE
		positiveClickListener {
			object : DialogPositiveCallback {
				override fun onPositiveClicked() {}
			}
		}
		negativeClickListener {
			object : DialogNegativeCallback {
				override fun onNegativeCallback() {}
			}
		}
	}.create()