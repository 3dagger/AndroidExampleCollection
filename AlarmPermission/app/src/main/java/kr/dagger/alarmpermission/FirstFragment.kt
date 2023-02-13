package kr.dagger.alarmpermission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.findNavController
import kr.dagger.alarmpermission.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

	private var _binding: FragmentFirstBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		_binding = FragmentFirstBinding.inflate(inflater, container, false)

		binding.btnGoAlarm.setOnClickListener {
			moveNotificationSetting()
		}
		return binding.root
	}

	private fun moveNotificationSetting() {
		val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			notificationSettingOreo(requireContext())
		} else {
			notificationSettingUnderOreo(requireContext())
		}

		requireContext().startActivity(intent)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun notificationSettingOreo(context: Context): Intent {
		return Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).also { intent ->
			intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
			intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
		}
	}

	private fun notificationSettingUnderOreo(context: Context): Intent {
		return Intent().also { intent ->
			intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
			intent.putExtra("app_package", context.packageName)
			intent.putExtra("app_uid", context.applicationInfo.uid)
		}
	}

	override fun onResume() {
		super.onResume()
		Log.d("leeam", "Notification is able? :: ${NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()}")
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.buttonFirst.setOnClickListener {
			findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}