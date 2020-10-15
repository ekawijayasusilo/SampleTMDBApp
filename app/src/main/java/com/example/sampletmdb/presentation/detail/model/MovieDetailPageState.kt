package com.example.sampletmdb.presentation.detail.model

sealed class MovieDetailPageState {
    data class Result(val movie: MovieDetailPageModel) : MovieDetailPageState()
    data class Error(val message: String) : MovieDetailPageState()
}