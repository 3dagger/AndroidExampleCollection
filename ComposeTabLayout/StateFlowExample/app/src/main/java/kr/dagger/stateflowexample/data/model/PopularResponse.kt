package kr.dagger.stateflowexample.data.model

import com.google.gson.annotations.SerializedName

data class PopularResponse(
	@SerializedName("page")
	val page: Int?,
	@SerializedName("results")
	val results: List<PopularResultsResponse>?
) {
	data class PopularResultsResponse(
		@SerializedName("adult")
		val adult: Boolean?,
		@SerializedName("backdrop_path")
		val backdropPath: String?,
		@SerializedName("id")
		val id: Int?,
		@SerializedName("overview")
		val overView: String?,
		@SerializedName("popularity")
		val popularity: Double?,
		@SerializedName("poster_path")
		val posterPath: String?,
		@SerializedName("release_date")
		val releaseDate: String?,
		@SerializedName("title")
		val title: String?,
		@SerializedName("vote_average")
		val voteAverage: Double?,
		@SerializedName("vote_count")
		val voteCount: Int?
	)
}