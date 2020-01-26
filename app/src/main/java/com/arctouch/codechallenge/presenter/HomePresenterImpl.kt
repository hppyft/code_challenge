package com.arctouch.codechallenge.presenter

import com.arctouch.codechallenge.model.data.MoviesDataSourceFactory
import com.arctouch.codechallenge.view.HomeView

class HomePresenterImpl(view: HomeView) : MovieListBasePresenter(MoviesDataSourceFactory.UPCOMING_MOVIES, view), HomePresenter {
    override fun tryAgain() {
        tryToGetMovies()
    }
}