package com.example.sampletmdb.data.list.datasource

import com.example.sampletmdb.data.MovieAPI
import com.example.sampletmdb.data.list.model.NowPlayingMoviesResponse
import com.example.sampletmdb.utils.unfold
import com.google.gson.Gson

class MoviesApiDataSource(private val api: MovieAPI, private val gson: Gson) : MoviesDataSource {
    override fun getNowPlayingMovies(page: Int) = api.getNowPlayingMovies(page).unfold(gson, NowPlayingMoviesResponse::toNowPlayingMovies)
}