package kr.dagger.customprogress

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kr.dagger.customprogress.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

	private var _binding: FragmentSecondBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		_binding = FragmentSecondBinding.inflate(inflater, container, false)
		return binding.root

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.progress.showProgress()

		Handler(Looper.getMainLooper()).postDelayed({
			binding.progress.dismissProgress()
		}, 5000)

//		binding.progress.visibility = View.VISIBLE

		binding.buttonSecond.setOnClickListener {
			findNavController().popBackStack()
//			findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}