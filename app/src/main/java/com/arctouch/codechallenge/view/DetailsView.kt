package com.arctouch.codechallenge.view

import com.arctouch.codechallenge.model.Movie

interface DetailsView {
    fun showMovie(movie: Movie)

    companion object {
        val MOVIE_ID = "com.arctouch.codechallenge.view.DetailsView.movieId"
    }
}