package kr.dagger.stateflowexample.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.dagger.stateflowexample.domain.model.Popular

interface MovieRepository {

	fun getPopularMovies(page: Int): Flow<Popular>
}