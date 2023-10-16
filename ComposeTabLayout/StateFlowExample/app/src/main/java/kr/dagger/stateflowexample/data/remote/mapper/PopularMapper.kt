package kr.dagger.stateflowexample.data.remote.mapper

import kr.dagger.stateflowexample.data.model.PopularResponse
import kr.dagger.stateflowexample.domain.model.Popular
import kr.dagger.stateflowexample.extension.defaultEmpty
import kr.dagger.stateflowexample.extension.defaultFalse
import kr.dagger.stateflowexample.extension.defaultZero

object PopularMapper {

	fun mapFromPopularResponseToModel(popularResponse: PopularResponse): Popular {
		return Popular(
			page = popularResponse.page.defaultZero(),
			results = popularResponse.results?.map {
				Popular.PopularResults(
					adult = it.adult.defaultFalse(),
					backdropPath = it.backdropPath.defaultEmpty(),
					id = it.id.defaultZero(),
					overView = it.overView.defaultEmpty(),
					popularity = it.popularity.defaultZero(),
					posterPath = it.posterPath.defaultEmpty(),
					releaseDate = it.releaseDate.defaultEmpty(),
					title = it.title.defaultEmpty(),
					voteAverage = it.voteAverage.defaultZero(),
					voteCount = it.voteCount.defaultZero()
				)
			}.defaultEmpty()
		)
	}
}