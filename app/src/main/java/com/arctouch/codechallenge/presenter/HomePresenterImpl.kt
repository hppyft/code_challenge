package com.arctouch.codechallenge.presenter

import com.arctouch.codechallenge.model.data.MoviesDataSourceFactory
import com.arctouch.codechallenge.view.HomeView

class HomePresenterImpl(val view: HomeView) : MovieListBasePresenter(MoviesDataSourceFactory.UPCOMING_MOVIES, view), HomePresenter {
}