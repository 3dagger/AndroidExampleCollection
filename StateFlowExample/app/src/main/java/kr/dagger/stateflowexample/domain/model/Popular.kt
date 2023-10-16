package kr.dagger.stateflowexample.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Popular(
	val page: Int,
	val results: List<PopularResults>
)
{
	@Parcelize
	data class PopularResults(
		val adult: Boolean,
		val backdropPath: String,
		val id: Int,
		val overView: String,
		val popularity: Double,
		val posterPath: String,
		val releaseDate: String,
		val title: String,
		val voteAverage: Double,
		val voteCount: Int
	) : Parcelable
}
