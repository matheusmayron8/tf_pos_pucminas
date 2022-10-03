package br.com.matheus.lima.trabalhofinalpospuc.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<Type : Any, Params : Any?> : UseCase<Type> {
    private val mChannel = MutableSharedFlow<Params>()

    suspend operator fun invoke(params: Params) = mChannel.emit(params)

    protected abstract fun doWork(params: Params): Flow<Type?>

    fun produce(params: Params): Flow<Type?> = doWork(params = params)
        .flowOn(dispatcher)

    override fun observe(): Flow<Type?> = mChannel.asSharedFlow()
        .distinctUntilChanged()
        .flatMapLatest { doWork(it) }
        .flowOn(dispatcher)

    object None
}
