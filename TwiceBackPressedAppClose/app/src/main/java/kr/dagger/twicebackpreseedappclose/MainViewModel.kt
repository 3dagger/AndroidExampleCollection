package kr.dagger.twicebackpreseedappclose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainViewModel : ViewModel() {
	private val _backPressedEvent: MutableSharedFlow<Unit> = MutableSharedFlow(
		replay = 0,
		extraBufferCapacity = 1,
		onBufferOverflow = BufferOverflow.DROP_OLDEST,
	)
	val backPressedEvent: SharedFlow<Unit> = _backPressedEvent.asSharedFlow()

	fun backPressedEvent() {
		_backPressedEvent.tryEmit(Unit)
	}
}