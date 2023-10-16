package kr.dagger.stateflowexample.presentation.first

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kr.dagger.stateflowexample.data.UiState
import kr.dagger.stateflowexample.domain.model.Popular
import kr.dagger.stateflowexample.domain.usecase.GetPopularMoviesParams
import kr.dagger.stateflowexample.domain.usecase.GetPopularMoviesUseCase
import javax.inject.Inject


@HiltViewModel
class FirstViewModel @Inject constructor(
	private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
	private val _popularMoviesStateFlow: MutableStateFlow<UiState<Popular>> = MutableStateFlow(UiState.Loading)
	val popularMoviesStateFlow: StateFlow<UiState<Popular>> = _popularMoviesStateFlow.asStateFlow()

	fun getPopularMovies() {
		viewModelScope.launch {
			getPopularMoviesUseCase.execute(GetPopularMoviesParams(page = 1)).catch {
				_popularMoviesStateFlow.value = UiState.Error(it.message.toString())
			}.collect {
				_popularMoviesStateFlow.value = UiState.Success(it)
			}
		}
	}
}