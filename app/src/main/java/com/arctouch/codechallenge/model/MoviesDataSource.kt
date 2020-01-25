package com.arctouch.codechallenge.model

import androidx.paging.ItemKeyedDataSource
import com.arctouch.codechallenge.model.api.TmdbApi
import com.arctouch.codechallenge.model.data.Cache
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSource(private val api: TmdbApi, private val compositeDisposable: CompositeDisposable) : ItemKeyedDataSource<Long, Movie>() {

    var page = 1L

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Movie>) {
        compositeDisposable.add(api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page++, TmdbApi.DEFAULT_REGION).subscribe({ movies ->
            callback.onResult(movies.results)
        }, { throwable -> }
        ))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Movie>) {
        compositeDisposable.add(api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page++, TmdbApi.DEFAULT_REGION).subscribe({ movies ->
            callback.onResult(movies.results)
        }, { throwable -> }
        ))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Movie>) {
        compositeDisposable.add(api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE).subscribe {
            Cache.cacheGenres(it.genres)
        })
    }

    override fun getKey(item: Movie): Long {
        return page
    }
}