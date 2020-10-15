package com.example.sampletmdb.presentation.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampletmdb.domain.list.interactor.GetNowPlayingMovies
import com.example.sampletmdb.domain.list.model.Movie
import com.example.sampletmdb.domain.list.model.NowPlayingMovies
import com.example.sampletmdb.presentation.list.model.MoviePageModel
import com.example.sampletmdb.presentation.list.model.MoviesPageState
import com.example.sampletmdb.utils.PageState

class MoviesViewModel(
    private val getNowPlayingMovies: GetNowPlayingMovies
) : ViewModel() {

    private val _liveData = MutableLiveData<PageState<MoviesPageState>>()
    val liveData: LiveData<PageState<MoviesPageState>>
        get() = _liveData

    private var nextPage = 1
    private var maxPage = Int.MAX_VALUE
    private var movies = listOf<MoviePageModel>()
    private var isLoadingPage = false
    private var isNextPageAvailable = true

    override fun onCleared() {
        getNowPlayingMovies.cancel()
        super.onCleared()
    }

    fun loadInitialPage() {
        fun onSuccess(nowPlayingMovies: NowPlayingMovies) {
            this.nextPage++
            this.maxPage = nowPlayingMovies.totalPages
            this.movies =
                nowPlayingMovies.movies.map { MoviePageModel.from(it) }.sortedBy { it.releaseDate }
            this.isNextPageAvailable =
                isNextPageAvailable(nowPlayingMovies.movies, this.nextPage, this.maxPage)
            this.isLoadingPage = false

            _liveData.value = PageState.Render(
                MoviesPageState.Result(
                    this.movies,
                    isFirstPageResult = true
                )
            )
        }

        fun onError(error: Throwable) {
            _liveData.value = PageState.Render(
                MoviesPageState.Error(
                    error.message.orEmpty(),
                    isFirstPageResult = true
                )
            )
        }

        this.nextPage = 1
        this.maxPage = Int.MAX_VALUE
        this.isNextPageAvailable = true
        this.isLoadingPage = true

        _liveData.value = PageState.Loading
        getNowPlayingMovies.cancel()
        getNowPlayingMovies(this.nextPage, ::onSuccess, ::onError)
    }

    private fun isNextPageAvailable(movies: List<Movie>, nextPage: Int, maxPage: Int): Boolean {
        return movies.isNotEmpty() && nextPage <= maxPage
    }

    fun loadNextPage() {
        fun onSuccessNextPage(nowPlayingMovies: NowPlayingMovies) {
            this.nextPage++
            this.maxPage = nowPlayingMovies.totalPages
            this.movies =
                (this.movies + nowPlayingMovies.movies.map { MoviePageModel.from(it) }).sortedBy { it.releaseDate }
            this.isNextPageAvailable =
                isNextPageAvailable(nowPlayingMovies.movies, this.nextPage, this.maxPage)
            this.isLoadingPage = false

            _liveData.value = PageState.Render(
                MoviesPageState.Result(
                    this.movies,
                    isFirstPageResult = false
                )
            )
        }

        fun onErrorNextPage(error: Throwable) {
            _liveData.value = PageState.Render(
                MoviesPageState.Error(
                    error.message.orEmpty(),
                    isFirstPageResult = false
                )
            )
        }

        if (!isLoadingPage && isNextPageAvailable) {
            isLoadingPage = true

            _liveData.value = PageState.Render(MoviesPageState.LoadingNextPage)
            getNowPlayingMovies(nextPage, ::onSuccessNextPage, ::onErrorNextPage)
        }
    }
}