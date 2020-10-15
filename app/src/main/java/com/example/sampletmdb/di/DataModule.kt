package com.example.sampletmdb.di

import com.example.sampletmdb.data.MovieAPI
import com.example.sampletmdb.data.detail.datasource.MovieDetailApiDataSource
import com.example.sampletmdb.data.detail.datasource.MovieDetailDataSource
import com.example.sampletmdb.data.list.datasource.MoviesApiDataSource
import com.example.sampletmdb.data.list.datasource.MoviesDataSource
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single<MovieAPI> {
        get<Retrofit>().create(MovieAPI::class.java)
    }

    factory<MoviesDataSource> {
        MoviesApiDataSource(get(), get())
    }

    factory<MovieDetailDataSource> {
        MovieDetailApiDataSource(get(), get())
    }
}