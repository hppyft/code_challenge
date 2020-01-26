package com.arctouch.codechallenge.model.data

import androidx.paging.ItemKeyedDataSource
import com.arctouch.codechallenge.model.api.TmdbApi
import io.reactivex.disposables.CompositeDisposable

abstract class MoviesDataSource(protected val api: TmdbApi, protected val compositeDisposable: CompositeDisposable) : ItemKeyedDataSource<Long, Movie>() {

    var page = 1L

    override fun getKey(item: Movie): Long {
        return page
    }
}