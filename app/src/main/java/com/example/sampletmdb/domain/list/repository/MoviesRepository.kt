package com.example.sampletmdb.domain.list.repository

import com.example.sampletmdb.domain.list.model.NowPlayingMovies
import io.reactivex.Single

interface MoviesRepository {
    fun getNowPlayingMovies(page: Int): Single<NowPlayingMovies>
}