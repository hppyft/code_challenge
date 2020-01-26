package com.arctouch.codechallenge.model.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arctouch.codechallenge.model.api.TmdbApi
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(private val compositeDisposable: CompositeDisposable, private val api: TmdbApi, private val dataSourceType: Int, var query: String = "") : DataSource.Factory<Long, Movie>() {

    companion object {
        val UPCOMING_MOVIES = 1
        val SEARCHED_MOVIES = 2
    }

    val moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()
    var moviesDataSource: MoviesDataSource? = null

    override fun create(): DataSource<Long, Movie> {
        moviesDataSource = when (dataSourceType) {
            SEARCHED_MOVIES -> MoviesDataSource(compositeDisposable) { page -> api.searchMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, query, page, TmdbApi.DEFAULT_REGION) }
            else -> MoviesDataSource(compositeDisposable) { page -> api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION) }
        }
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource!!
    }

    fun invalidate() {
        moviesDataSource?.invalidate()
    }
}