package com.example.sampletmdb.data.detail.datasource

import com.example.sampletmdb.domain.detail.model.MovieDetail
import io.reactivex.Single

interface MovieDetailDataSource {
    fun getMovieDetail(id: Int): Single<MovieDetail>
}