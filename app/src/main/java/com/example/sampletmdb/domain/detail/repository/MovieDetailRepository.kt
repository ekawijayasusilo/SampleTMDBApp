package com.example.sampletmdb.domain.detail.repository

import com.example.sampletmdb.domain.detail.model.MovieDetail
import io.reactivex.Single

interface MovieDetailRepository {
    fun getMovieDetail(id: Int): Single<MovieDetail>
}