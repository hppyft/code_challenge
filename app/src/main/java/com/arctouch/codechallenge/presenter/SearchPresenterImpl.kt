package com.arctouch.codechallenge.presenter

import com.arctouch.codechallenge.model.data.MoviesDataSourceFactory
import com.arctouch.codechallenge.view.SearchView

class SearchPresenterImpl(view: SearchView) : MovieListBasePresenter(MoviesDataSourceFactory.SEARCHED_MOVIES, view), SearchPresenter {

    override fun onQueryChanged(query: String) {
        sourceFactory.query = query
        sourceFactory.invalidate()
    }
}