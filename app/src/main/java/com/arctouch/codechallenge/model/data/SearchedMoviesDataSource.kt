package com.arctouch.codechallenge.model.data

import com.arctouch.codechallenge.model.api.TmdbApi
import io.reactivex.disposables.CompositeDisposable

class SearchedMoviesDataSource(api: TmdbApi, compositeDisposable: CompositeDisposable, val query: String) : MoviesDataSource(api, compositeDisposable) {

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Movie>) {
        compositeDisposable.add(api.searchMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, query, page++, TmdbApi.DEFAULT_REGION).subscribe({ movies ->
            callback.onResult(movies.results)
        }, { throwable -> }
        ))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Movie>) {
        compositeDisposable.add(api.searchMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, query, page++, TmdbApi.DEFAULT_REGION).subscribe({ movies ->
            callback.onResult(movies.results)
        }, { throwable -> }
        ))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Movie>) {
        if (Cache.genres.isNullOrEmpty()) {
            compositeDisposable.add(api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE).subscribe {
                Cache.cacheGenres(it.genres)
            })
        }
    }
}