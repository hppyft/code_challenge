package com.arctouch.codechallenge.presenter

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.arctouch.codechallenge.model.Movie

interface HomePresenter {
    fun getList(): LiveData<PagedList<Movie>>
    fun onDestroy()
}