package com.arctouch.codechallenge.model.data

import com.arctouch.codechallenge.model.api.TmdbApi
import io.reactivex.disposables.CompositeDisposable

class UpcomingMoviesDataSource(api: TmdbApi, compositeDisposable: CompositeDisposable) : MoviesDataSource(api, compositeDisposable) {

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
}