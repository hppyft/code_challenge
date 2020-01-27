package com.arctouch.codechallenge.model.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arctouch.codechallenge.model.api.TmdbApi
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(private val compositeDisposable: CompositeDisposable, private val api: TmdbApi, var query: String = "") : DataSource.Factory<Long, Movie>() {

    private val moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()
    var moviesDataSource: MoviesDataSource? = null

    override fun create(): DataSource<Long, Movie> {
        moviesDataSource = if (query != "") MoviesDataSource(compositeDisposable) { page -> api.searchMovies(query = query, page = page) }
        else MoviesDataSource(compositeDisposable) { page -> api.upcomingMovies(page = page) }

        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource!!
    }

    fun invalidate() {
        moviesDataSource?.invalidate()
    }
}