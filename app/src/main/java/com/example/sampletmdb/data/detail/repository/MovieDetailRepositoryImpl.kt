package com.example.sampletmdb.data.detail.repository

import com.example.sampletmdb.data.detail.datasource.MovieDetailDataSource
import com.example.sampletmdb.domain.detail.repository.MovieDetailRepository

class MovieDetailRepositoryImpl(private val dataSource: MovieDetailDataSource) : MovieDetailRepository {
    override fun getMovieDetail(id: Int) = dataSource.getMovieDetail(id)
}