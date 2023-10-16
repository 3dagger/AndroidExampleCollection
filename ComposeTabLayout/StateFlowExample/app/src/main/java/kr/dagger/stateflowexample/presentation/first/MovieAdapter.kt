package kr.dagger.stateflowexample.presentation.first


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.dagger.stateflowexample.R
import kr.dagger.stateflowexample.data.UiState
import kr.dagger.stateflowexample.databinding.MovieItemBinding
import kr.dagger.stateflowexample.domain.model.Popular

class MovieAdapter(
	private val itemClickListener: MovieItemClickListener
) : ListAdapter<Popular.PopularResults, MovieAdapter.ViewHolder>(diffUtil) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			MovieItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(currentList[position])
	}

	inner class ViewHolder(private val binding: MovieItemBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(item: Popular.PopularResults) {
			binding.apply {
				Glide.with(binding.root)
					.load("https://image.tmdb.org/t/p/original/${item.posterPath}")
					.transition(DrawableTransitionOptions.withCrossFade())
					.into(binding.ivMovie)

				root.setOnClickListener {
					itemClickListener.onItemClicked(item)
				}
			}
		}
	}

	companion object {
		val diffUtil = object : DiffUtil.ItemCallback<Popular.PopularResults>() {
			override fun areItemsTheSame(
				oldItem: Popular.PopularResults,
				newItem: Popular.PopularResults
			): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(
				oldItem: Popular.PopularResults,
				newItem: Popular.PopularResults
			): Boolean {
				return oldItem == newItem
			}
		}
	}
}