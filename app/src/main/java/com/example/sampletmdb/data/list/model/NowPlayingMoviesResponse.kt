package com.example.sampletmdb.data.list.model

import com.example.sampletmdb.domain.list.model.NowPlayingMovies
import com.google.gson.annotations.SerializedName

class NowPlayingMoviesResponse(
    @SerializedName("results") val movies: List<MovieResponse>,
    @SerializedName("total_results") val totalMovies: Int,
    @SerializedName("dates") val releaseDateRange: ReleaseDateResponse,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int
) {
    fun toNowPlayingMovies() = NowPlayingMovies(movies.map(MovieResponse::toMovie), totalPages)
}