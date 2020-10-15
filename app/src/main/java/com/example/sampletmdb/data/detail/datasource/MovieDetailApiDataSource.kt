package com.example.sampletmdb.data.detail.datasource

import com.example.sampletmdb.data.MovieAPI
import com.example.sampletmdb.data.detail.model.MovieDetailResponse
import com.example.sampletmdb.utils.unfold
import com.google.gson.Gson

class MovieDetailApiDataSource(private val api: MovieAPI, private val gson: Gson): MovieDetailDataSource {
    override fun getMovieDetail(id: Int) = api.getMovieDetail(id).unfold(gson, MovieDetailResponse::toMovieDetail)
}