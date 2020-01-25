package com.arctouch.codechallenge.view

import com.arctouch.codechallenge.model.Movie

interface HomeView {
    fun showMovies(moviesWithGenres: List<Movie>)
}