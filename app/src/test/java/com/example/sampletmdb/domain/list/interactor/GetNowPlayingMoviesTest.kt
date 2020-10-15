package com.example.sampletmdb.domain.list.interactor

import com.example.sampletmdb.domain.SchedulerProvider
import com.example.sampletmdb.domain.list.model.NowPlayingMovies
import com.example.sampletmdb.domain.list.repository.MoviesRepository
import com.example.sampletmdb.utils.TrampolineSchedulerProvider
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class GetNowPlayingMoviesTest {

    companion object{
        @JvmStatic
        private lateinit var getNowPlayingMovies: GetNowPlayingMovies

        @JvmStatic
        private lateinit var schedulerProvider: SchedulerProvider
        @JvmStatic
        private lateinit var repository: MoviesRepository
        @JvmStatic
        private lateinit var onSuccess: (NowPlayingMovies) -> Unit
        @JvmStatic
        private lateinit var onError: (Throwable) -> Unit

        @BeforeClass
        @JvmStatic
        fun setUp() {
            schedulerProvider = TrampolineSchedulerProvider()
            repository = mock()
            onSuccess = mock()
            onError = mock()

            getNowPlayingMovies = GetNowPlayingMovies(schedulerProvider, repository)
        }
    }

    @Before
    fun prepare() {
        reset(repository, onSuccess, onError)
    }

    @Test
    fun `(invoke) given success result, when invoke is called, then should call onSuccess`() {
        // given
        val page = 10
        val expected = NowPlayingMovies.empty()
        whenever(
            repository.getNowPlayingMovies(any())
        ).thenReturn(Single.just(expected))
        whenever(
            onSuccess.invoke(any())
        ).thenReturn(Unit)

        // when
        getNowPlayingMovies.invoke(page, onSuccess, onError)

        // then
        verify(repository).getNowPlayingMovies(same(page))
        verifyNoMoreInteractions(repository)
        verify(onSuccess).invoke(eq(expected))
        verifyZeroInteractions(onError)
    }

    @Test
    fun `(invoke) given error result, when invoke is called, then should call onError`() {
        // given
        val page = 10
        val expected = Exception()
        whenever(
            repository.getNowPlayingMovies(any())
        ).thenReturn(Single.error(expected))
        whenever(
            onError.invoke(any())
        ).thenReturn(Unit)

        // when
        getNowPlayingMovies.invoke(page, onSuccess, onError)

        // then
        verify(repository).getNowPlayingMovies(same(page))
        verifyNoMoreInteractions(repository)
        verify(onError).invoke(eq(expected))
        verifyZeroInteractions(onSuccess)
    }
}