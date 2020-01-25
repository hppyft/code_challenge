package com.arctouch.codechallenge

import com.arctouch.codechallenge.model.Movie

interface HomeView {
    fun showMovies(moviesWithGenres: List<Movie>)
}