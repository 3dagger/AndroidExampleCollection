package kr.dagger.stateflowexample.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.dagger.stateflowexample.data.remote.MovieService
import kr.dagger.stateflowexample.data.remote.mapper.PopularMapper
import kr.dagger.stateflowexample.domain.model.Popular
import kr.dagger.stateflowexample.domain.repository.MovieRepository

class MovieRepositoryImpl(
	private val movieService: MovieService
) : MovieRepository {

	override fun getPopularMovies(page: Int): Flow<Popular> {
		return flow {
			emit(
				PopularMapper.mapFromPopularResponseToModel(
					movieService.getPopularMovies(page)
				)
			)
		}
	}
}