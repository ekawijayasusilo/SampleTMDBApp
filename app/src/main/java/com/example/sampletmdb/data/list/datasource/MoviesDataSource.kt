package com.example.sampletmdb.data.list.datasource

import com.example.sampletmdb.domain.list.model.NowPlayingMovies
import io.reactivex.Single

interface MoviesDataSource {
    fun getNowPlayingMovies(page: Int): Single<NowPlayingMovies>
}