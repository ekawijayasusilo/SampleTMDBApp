package com.example.sampletmdb.domain.list.interactor

import com.example.sampletmdb.domain.BaseUseCase
import com.example.sampletmdb.domain.SchedulerProvider
import com.example.sampletmdb.domain.list.model.NowPlayingMovies
import com.example.sampletmdb.domain.list.repository.MoviesRepository

open class GetNowPlayingMovies(
    schedulerProvider: SchedulerProvider,
    private val repository: MoviesRepository
) : BaseUseCase<Int, NowPlayingMovies>(schedulerProvider) {
    override fun buildObservable(request: Int) = repository.getNowPlayingMovies(request)
}