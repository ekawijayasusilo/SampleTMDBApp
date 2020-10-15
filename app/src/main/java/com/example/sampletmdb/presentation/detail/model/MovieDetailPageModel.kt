package com.example.sampletmdb.presentation.detail.model

import com.example.sampletmdb.domain.detail.model.MovieDetail
import com.example.sampletmdb.utils.round2Decimal
import com.example.sampletmdb.utils.toFullImageUrl

data class MovieDetailPageModel(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val popularity: String,
    val overview: String,
    val genres: List<String>,
    val language: String,
    val runtime: String
) {
    companion object {
        private fun Int?.toRuntime() = if (this != null) "$this mins" else "Unknown"

        fun from(movieDetail: MovieDetail) = MovieDetailPageModel(
            movieDetail.id,
            movieDetail.title,
            movieDetail.posterPath.toFullImageUrl(),
            movieDetail.backdropPath.toFullImageUrl(),
            movieDetail.popularity.round2Decimal(),
            movieDetail.overview,
            movieDetail.genres,
            movieDetail.language.toUpperCase(),
            movieDetail.runtime.toRuntime()
        )

        fun empty() = MovieDetailPageModel(
            -1,
            "-",
            null,
            null,
            "-",
            "-",
            listOf("-"),
            "-",
            "-"
        )
    }
}