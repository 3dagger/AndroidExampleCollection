package kr.dagger.sharedelementtransitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionSet
import kr.dagger.sharedelementtransitions.databinding.FragmentSimpleBBinding
import java.util.concurrent.TimeUnit

class SimpleFragmentB : Fragment() {
	private var _binding: FragmentSimpleBBinding? = null
	val binding get() = _binding!!

	companion object {
		const val TRANSITION_NAME_IMAGE = "transition_recomm_product_image"
		const val TRANSITION_NAME_TITLE = "transition_recomm_product_title"
		const val TRANSITION_NAME_TYPE = "transition_recomm_product_type"
		const val TRANSITION_NAME_INFO = "transition_recomm_product_info"
		const val TRANSITION_NAME_PRICE = "transition_recomm_product_price"
		const val TRANSITION_NAME_EMP_NAME = "transition_recomm_product_employee_name"
		const val TRANSITION_NAME_EMP_PROFILE = "transition_recomm_product_employee_profile"
		const val TRANSITION_NAME_EMP_DEPT = "transition_recomm_product_employee_dept"
		const val TRANSITION_NAME_EMP_REASON = "transition_recomm_product_employee_reason"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val enterAnimation = TransitionInflater.from(requireContext()).inflateTransition(
			R.transition.image_shared_element_transition
		)
		val returnAnimation = TransitionInflater.from(requireContext()).inflateTransition(
			R.transition.image_shared_element_transition
		)
		sharedElementEnterTransition = enterAnimation
		sharedElementReturnTransition = returnAnimation

		(sharedElementEnterTransition as TransitionSet).addListener(object :
			TransitionListenerAdapter() {
			override fun onTransitionEnd(transition: Transition) {
				super.onTransitionEnd(transition)
				binding.viewClose.clClose.visibility = View.VISIBLE
			}
		})
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSimpleBBinding.inflate(inflater, container, false)
		binding.lifecycleOwner = this@SimpleFragmentB
		binding.fm = this@SimpleFragmentB
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		postponeEnterTransition(100L, TimeUnit.MILLISECONDS)
	}
}