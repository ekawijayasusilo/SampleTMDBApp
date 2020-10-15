package com.example.sampletmdb.presentation.list.model

import com.example.sampletmdb.domain.list.model.Movie
import com.example.sampletmdb.utils.round2Decimal
import com.example.sampletmdb.utils.toFullImageUrl

data class MoviePageModel(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val popularity: String,
    val releaseDate: Long
) {
    companion object {
        fun from(movie: Movie) = MoviePageModel(
            movie.id,
            movie.title,
            movie.posterPath.toFullImageUrl(),
            movie.popularity.round2Decimal(),
            movie.releaseDate.time
        )
    }
}