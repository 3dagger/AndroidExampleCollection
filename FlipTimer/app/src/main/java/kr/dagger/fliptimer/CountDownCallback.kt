package kr.dagger.fliptimer

interface CountDownCallback {
	fun countdownAboutToFinish()
	fun countdownFinished()
}