package com.example.sampletmdb.domain

import com.example.sampletmdb.utils.launch
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

abstract class BaseUseCase<REQUEST, RESPONSE>(
    private val schedulerProvider: SchedulerProvider
) {
    private val disposables = CompositeDisposable()

    open operator fun invoke(
        request: REQUEST,
        onSuccess: (RESPONSE) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        disposables.launch {
            buildObservable(request)
                .subscribeOn(schedulerProvider.executionThread())
                .observeOn(schedulerProvider.postExecutionThread())
                .subscribe(onSuccess, onError)
        }
    }

    protected abstract fun buildObservable(request: REQUEST): Single<RESPONSE>

    open fun cancel() {
        disposables.clear()
    }
}