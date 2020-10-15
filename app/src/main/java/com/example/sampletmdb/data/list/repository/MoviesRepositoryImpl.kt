package com.example.sampletmdb.data.list.repository

import com.example.sampletmdb.data.list.datasource.MoviesDataSource
import com.example.sampletmdb.domain.list.repository.MoviesRepository

class MoviesRepositoryImpl(private val dataSource: MoviesDataSource) : MoviesRepository {
    override fun getNowPlayingMovies(page: Int) = dataSource.getNowPlayingMovies(page)
}