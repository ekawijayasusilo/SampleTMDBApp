package com.example.sampletmdb.domain.detail.interactor

import com.example.sampletmdb.domain.BaseUseCase
import com.example.sampletmdb.domain.SchedulerProvider
import com.example.sampletmdb.domain.detail.model.MovieDetail
import com.example.sampletmdb.domain.detail.repository.MovieDetailRepository

open class GetMovieDetail(
    schedulerProvider: SchedulerProvider,
    private val repository: MovieDetailRepository
) : BaseUseCase<Int, MovieDetail>(schedulerProvider) {
    override fun buildObservable(request: Int) = repository.getMovieDetail(request)
}