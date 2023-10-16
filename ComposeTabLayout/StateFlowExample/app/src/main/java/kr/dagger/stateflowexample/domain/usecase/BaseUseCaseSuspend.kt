package kr.dagger.composetmdb.domain.usecase

abstract class BaseUseCaseSuspend<in Params, out T> {

	abstract suspend fun execute(params: Params): T
}