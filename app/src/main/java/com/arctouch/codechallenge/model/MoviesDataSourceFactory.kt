package com.arctouch.codechallenge.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arctouch.codechallenge.model.api.TmdbApi
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(private val compositeDisposable: CompositeDisposable, private val api: TmdbApi) : DataSource.Factory<Long, Movie>() {

    val moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Long, Movie> {
        val moviesDataSource = MoviesDataSource(api, compositeDisposable)
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }
}