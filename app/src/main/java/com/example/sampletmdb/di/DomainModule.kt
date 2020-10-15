package com.example.sampletmdb.di

import com.example.sampletmdb.data.detail.repository.MovieDetailRepositoryImpl
import com.example.sampletmdb.data.list.repository.MoviesRepositoryImpl
import com.example.sampletmdb.domain.IOSchedulerProvider
import com.example.sampletmdb.domain.SchedulerProvider
import com.example.sampletmdb.domain.detail.interactor.GetMovieDetail
import com.example.sampletmdb.domain.detail.repository.MovieDetailRepository
import com.example.sampletmdb.domain.list.interactor.GetNowPlayingMovies
import com.example.sampletmdb.domain.list.repository.MoviesRepository
import org.koin.dsl.module

val domainModule = module {
    single<SchedulerProvider> {
        IOSchedulerProvider()
    }

    factory<MoviesRepository> {
        MoviesRepositoryImpl(get())
    }

    factory {
        GetNowPlayingMovies(get(), get())
    }

    factory<MovieDetailRepository> {
        MovieDetailRepositoryImpl(get())
    }

    factory {
        GetMovieDetail(get(), get())
    }
}