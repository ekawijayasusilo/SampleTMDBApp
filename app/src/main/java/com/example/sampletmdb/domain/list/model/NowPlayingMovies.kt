package com.example.sampletmdb.domain.list.model

data class NowPlayingMovies(
    val movies: List<Movie>,
    val totalPages: Int
) {
    companion object {
        fun empty() = NowPlayingMovies(listOf(Movie.empty()), 1)
    }
}