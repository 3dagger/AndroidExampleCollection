package kr.dagger.sharedelementtransitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import kr.dagger.sharedelementtransitions.databinding.FragmentSimpleABinding
import java.util.concurrent.TimeUnit

class SimpleFragmentA : Fragment() {
	private var _binding: FragmentSimpleABinding? = null
	val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSimpleABinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.ivImage.setOnClickListener {
			postponeEnterTransition(100L, TimeUnit.MILLISECONDS)

			val extras = FragmentNavigatorExtras(
				binding.ivImage to SimpleFragmentB.TRANSITION_NAME_IMAGE,
				binding.tvRecommend to SimpleFragmentB.TRANSITION_NAME_TYPE,
				binding.tvTitle to SimpleFragmentB.TRANSITION_NAME_TITLE,
				binding.tvInfo to SimpleFragmentB.TRANSITION_NAME_INFO,
				binding.tvPrice to SimpleFragmentB.TRANSITION_NAME_PRICE,
				binding.ivEmployeeProfile to SimpleFragmentB.TRANSITION_NAME_EMP_PROFILE,
				binding.tvEmployeeName to SimpleFragmentB.TRANSITION_NAME_EMP_NAME,
				binding.tvEmployeeDept to SimpleFragmentB.TRANSITION_NAME_EMP_DEPT,
				binding.tvEmployeeReason to SimpleFragmentB.TRANSITION_NAME_EMP_REASON
			)

			findNavController().navigate(
				R.id.action_simpleFragmentA_to_simpleFragmentB,
				null,
				null,
				extras
			)
		}
	}
}