package com.arctouch.codechallenge.presenter

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.arctouch.codechallenge.model.data.Movie

interface SearchPresenter {
    fun getList(): LiveData<PagedList<Movie>>
    fun onDestroy()
    fun onQueryChanged(query: String)
}