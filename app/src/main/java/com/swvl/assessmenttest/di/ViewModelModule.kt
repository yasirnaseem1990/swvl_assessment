package com.swvl.assessmenttest.di

import com.swvl.assessmenttest.viewmodel.movies.MovieDetailsViewModel
import com.swvl.assessmenttest.viewmodel.movies.MoviesListViewModel
import com.swvl.assessmenttest.viewmodel.splash.SplashViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel



val viewModelModule = module {

    viewModel {
        SplashViewModel( get())
    }

    viewModel {
        MoviesListViewModel( get())
    }

    viewModel { (model: String) ->
        MovieDetailsViewModel( model, get())
    }
}