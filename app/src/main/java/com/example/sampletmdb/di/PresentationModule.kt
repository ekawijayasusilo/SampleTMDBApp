package com.example.sampletmdb.di

import com.example.sampletmdb.presentation.detail.viewmodel.MovieDetailViewModel
import com.example.sampletmdb.presentation.list.viewmodel.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MoviesViewModel(get()) }

    viewModel { MovieDetailViewModel(get()) }
}