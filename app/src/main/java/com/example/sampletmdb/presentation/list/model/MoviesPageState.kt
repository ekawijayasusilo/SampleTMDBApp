package com.example.sampletmdb.presentation.list.model

sealed class MoviesPageState {
    data class Result(val movies: List<MoviePageModel>, val isFirstPageResult: Boolean) : MoviesPageState()
    data class Error(val message: String, val isFirstPageResult: Boolean) : MoviesPageState()
    object LoadingNextPage : MoviesPageState()
}