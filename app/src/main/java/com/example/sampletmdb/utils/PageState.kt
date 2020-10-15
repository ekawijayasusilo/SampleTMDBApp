package com.example.sampletmdb.utils

sealed class PageState<out T> {
    object Loading : PageState<Nothing>()
    data class Render<T>(val renderState: T) : PageState<T>()
}