package kr.dagger.stateflowexample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.dagger.stateflowexample.data.remote.MovieService
import kr.dagger.stateflowexample.data.repository.MovieRepositoryImpl
import kr.dagger.stateflowexample.domain.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

	@Provides
	@Singleton
	fun provideMovieRepository(movieService: MovieService): MovieRepository {
		return MovieRepositoryImpl(movieService)
	}
}