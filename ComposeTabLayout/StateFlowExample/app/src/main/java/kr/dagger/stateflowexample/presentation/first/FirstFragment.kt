package kr.dagger.stateflowexample.presentation.first

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.dagger.stateflowexample.R
import kr.dagger.stateflowexample.data.UiState
import kr.dagger.stateflowexample.databinding.FragmentFirstBinding
import kr.dagger.stateflowexample.domain.model.Popular
import kr.dagger.stateflowexample.extension.showSimpleDialog
import kr.dagger.stateflowexample.presentation.second.SecondFragment

@AndroidEntryPoint
class FirstFragment : Fragment(), MovieItemClickListener {
	private lateinit var binding: FragmentFirstBinding
	private val viewModel: FirstViewModel by viewModels()
	private val adapter: MovieAdapter by lazy { MovieAdapter(this) }

	companion object {
		const val SPAN_COUNT = 2
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentFirstBinding.inflate(inflater, container, false)
		binding.lifecycleOwner = viewLifecycleOwner
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.getPopularMovies()
		initView()
		subscribeObserve()
	}

	private fun initView() {
		binding.recyclerView.apply {
			layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
			adapter = this@FirstFragment.adapter
		}
	}

	override fun onItemClicked(item: Popular.PopularResults) {
		val bundle = SecondFragment.BundleBuilder.createIntent(item)
		findNavController().navigate(
			resId = R.id.action_firstFragment_to_secondFragment,
			args = bundle,
			navOptions = null,
			navigatorExtras = null
		)
	}

	private fun subscribeObserve() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.CREATED) {
				viewModel.popularMoviesStateFlow.collect { uiState ->
					when (uiState) {
						is UiState.Loading -> Toast.makeText(requireActivity(), "Loading", Toast.LENGTH_SHORT).show()
						is UiState.Success -> adapter.submitList(uiState.data.results)
						is UiState.Error -> showSimpleDialog(uiState.errorMessage).show()
					}
				}
			}
		}
	}
}