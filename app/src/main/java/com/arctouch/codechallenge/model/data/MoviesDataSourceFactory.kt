package com.arctouch.codechallenge.model.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arctouch.codechallenge.model.api.MoviesModel
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(private val compositeDisposable: CompositeDisposable, private val api: MoviesModel, private val dataSourceType: Int, var query: String = "") : DataSource.Factory<Long, Movie>() {

    companion object {
        val UPCOMING_MOVIES = 1
        val SEARCHED_MOVIES = 2
    }

    val moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()
    var moviesDataSource: MoviesDataSource? = null

    override fun create(): DataSource<Long, Movie> {
        moviesDataSource = when (dataSourceType) {
            SEARCHED_MOVIES -> MoviesDataSource(compositeDisposable) { page -> api.searchMovies(MoviesModel.API_KEY, MoviesModel.DEFAULT_LANGUAGE, query, page, MoviesModel.DEFAULT_REGION) }
            else -> MoviesDataSource(compositeDisposable) { page -> api.upcomingMovies(MoviesModel.API_KEY, MoviesModel.DEFAULT_LANGUAGE, page, MoviesModel.DEFAULT_REGION) }
        }
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource!!
    }

    fun invalidate() {
        moviesDataSource?.invalidate()
    }
}