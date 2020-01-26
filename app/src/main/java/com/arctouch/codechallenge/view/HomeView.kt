package com.arctouch.codechallenge.view

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.arctouch.codechallenge.model.data.Movie

interface HomeView {
    fun setList(list: LiveData<PagedList<Movie>>)
    fun showNoMovies()
}