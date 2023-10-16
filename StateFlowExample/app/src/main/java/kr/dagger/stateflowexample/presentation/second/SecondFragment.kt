package kr.dagger.stateflowexample.presentation.second

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kr.dagger.stateflowexample.databinding.FragmentSecondBinding
import kr.dagger.stateflowexample.domain.model.Popular

class SecondFragment : Fragment() {
	private lateinit var binding: FragmentSecondBinding

	object BundleBuilder {
		private const val ITEM = "ITEM"

		fun createIntent(item: Popular.PopularResults): Bundle {
			return Bundle().apply {
				putParcelable(ITEM, item)
			}
		}

		fun getItem(arguments: Bundle?): Popular.PopularResults? =
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				arguments?.getParcelable(ITEM, Popular.PopularResults::class.java)
			} else {
				arguments?.getParcelable(ITEM)
			}
	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
		binding.lifecycleOwner = viewLifecycleOwner
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.apply {
			Glide.with(requireContext())
				.load("https://image.tmdb.org/t/p/original/${BundleBuilder.getItem(arguments)?.posterPath}")
				.transition(DrawableTransitionOptions.withCrossFade())
				.into(ivMovieDetail)

			tvTitleDetail.text = BundleBuilder.getItem(arguments)?.title
		}
	}
}