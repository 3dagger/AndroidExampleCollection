package kr.dagger.stateflowexample.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.dagger.composetmdb.domain.usecase.BaseUseCaseSuspend
import kr.dagger.stateflowexample.domain.model.Popular
import kr.dagger.stateflowexample.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
	private val movieRepository: MovieRepository
) : BaseUseCaseSuspend<GetPopularMoviesParams, Flow<Popular>>() {

	override suspend fun execute(params: GetPopularMoviesParams): Flow<Popular> {
		return movieRepository.getPopularMovies(params.page)
	}
}

class GetPopularMoviesParams(
	val page: Int
)