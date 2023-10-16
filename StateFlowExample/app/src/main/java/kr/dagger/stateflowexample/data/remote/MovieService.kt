package kr.dagger.stateflowexample.data.remote

import kr.dagger.stateflowexample.data.model.PopularResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

	@GET("/3/movie/popular?language=ko-KR")
	suspend fun getPopularMovies(
		@Query("page") page: Int
	) : PopularResponse
}