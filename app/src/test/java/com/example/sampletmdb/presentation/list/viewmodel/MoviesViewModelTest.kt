package com.example.sampletmdb.presentation.list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.sampletmdb.domain.list.interactor.GetNowPlayingMovies
import com.example.sampletmdb.domain.list.model.NowPlayingMovies
import com.example.sampletmdb.presentation.list.model.MoviePageModel
import com.example.sampletmdb.presentation.list.model.MoviesPageState
import com.example.sampletmdb.utils.PageState
import com.example.sampletmdb.utils.RxImmediateSchedulerRule
import com.nhaarman.mockitokotlin2.*
import org.junit.*

class MoviesViewModelTest {

    companion object {
        @JvmStatic
        private lateinit var viewModel: MoviesViewModel

        @JvmStatic
        private lateinit var getNowPlayingMovies: GetNowPlayingMovies

        @JvmStatic
        private lateinit var observer: Observer<PageState<MoviesPageState>>

        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()

        @ClassRule
        @JvmField
        var rule = InstantTaskExecutorRule()

        @BeforeClass
        @JvmStatic
        fun setUp() {
            getNowPlayingMovies = mock()
            observer = mock()
        }
    }

    @Before
    fun prepare() {
        reset(getNowPlayingMovies, observer)

        viewModel = MoviesViewModel(getNowPlayingMovies)
        viewModel.liveData.observeForever(observer)
    }

    @After
    fun deconstruct() {
        viewModel.liveData.removeObserver(observer)
    }

    @Test
    fun `(loadInitialPage) given success result, when loadInitialPage is called, then should post successful render state`() {
        // given
        val onSuccess = argumentCaptor<(NowPlayingMovies) -> Unit>()
        val response = NowPlayingMovies.empty()
        val expectedState = PageState.Render(
            MoviesPageState.Result(
                response.movies.map { MoviePageModel.from(it) },
                isFirstPageResult = true
            )
        )
        whenever(
            getNowPlayingMovies.invoke(any(), onSuccess.capture(), any())
        ).then {
            onSuccess.firstValue.invoke(response)
        }

        // when
        viewModel.loadInitialPage()

        // then
        verify(observer).onChanged(PageState.Loading)
        verify(getNowPlayingMovies).cancel()
        verify(getNowPlayingMovies).invoke(same(1), any(), any())
        verify(observer).onChanged(eq(expectedState))
        verifyNoMoreInteractions(getNowPlayingMovies)
    }

    @Test
    fun `(loadInitialPage) given error result, when loadInitialPage is called, then should post erroneous render state`() {
        // given
        val onError = argumentCaptor<(Throwable) -> Unit>()
        val response = Exception("message")
        val expectedState = PageState.Render(
            MoviesPageState.Error(
                response.message.orEmpty(),
                isFirstPageResult = true
            )
        )
        whenever(
            getNowPlayingMovies.invoke(any(), any(), onError.capture())
        ).then {
            onError.firstValue.invoke(response)
        }

        // when
        viewModel.loadInitialPage()

        // then
        verify(observer).onChanged(PageState.Loading)
        verify(getNowPlayingMovies).cancel()
        verify(getNowPlayingMovies).invoke(same(1), any(), any())
        verify(observer).onChanged(expectedState)
        verifyNoMoreInteractions(getNowPlayingMovies)
    }

    @Test
    fun `(loadNextPage) given success result, when loadNextPage is called, then should post successful render state`() {
        // given
        val onSuccess = argumentCaptor<(NowPlayingMovies) -> Unit>()
        val response = NowPlayingMovies.empty()
        val expectedState = PageState.Render(
            MoviesPageState.Result(
                response.movies.map { MoviePageModel.from(it) },
                isFirstPageResult = false
            )
        )
        whenever(
            getNowPlayingMovies.invoke(any(), onSuccess.capture(), any())
        ).then {
            onSuccess.firstValue.invoke(response)
        }

        // when
        viewModel.loadNextPage()

        // then
        verify(observer).onChanged(PageState.Render(MoviesPageState.LoadingNextPage))
        verify(getNowPlayingMovies).invoke(same(1), any(), any())
        verify(observer).onChanged(expectedState)
        verifyNoMoreInteractions(getNowPlayingMovies)
    }

    @Test
    fun `(loadNextPage) given error result, when loadNextPage is called, then should post erroneous render state`() {
        // given
        val onError = argumentCaptor<(Throwable) -> Unit>()
        val response = Exception("message")
        val expectedState = PageState.Render(
            MoviesPageState.Error(
                response.message.orEmpty(),
                isFirstPageResult = false
            )
        )
        whenever(
            getNowPlayingMovies.invoke(any(), any(), onError.capture())
        ).then {
            onError.firstValue.invoke(response)
        }

        // when
        viewModel.loadNextPage()

        // then
        verify(observer).onChanged(PageState.Render(MoviesPageState.LoadingNextPage))
        verify(getNowPlayingMovies).invoke(same(1), any(), any())
        verify(observer).onChanged(expectedState)
        verifyNoMoreInteractions(getNowPlayingMovies)
    }
}