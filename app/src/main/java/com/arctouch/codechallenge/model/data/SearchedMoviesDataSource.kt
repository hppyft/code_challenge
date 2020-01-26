package com.arctouch.codechallenge.model.data

import com.arctouch.codechallenge.model.api.TmdbApi
import io.reactivex.disposables.CompositeDisposable

class SearchedMoviesDataSource(api: TmdbApi, compositeDisposable: CompositeDisposable, val query: String) : MoviesDataSource(api, compositeDisposable) {

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Movie>) {
        compositeDisposable.add(api.searchMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, query, page++, TmdbApi.DEFAULT_REGION).subscribe({ movies ->
            val moviesWithGenres = movies.results.map { movie ->
                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
            }
            callback.onResult(moviesWithGenres)
        }, { throwable -> }
        ))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Movie>) {
        compositeDisposable.add(api.searchMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, query, page++, TmdbApi.DEFAULT_REGION).subscribe({ movies ->
            val moviesWithGenres = movies.results.map { movie ->
                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
            }
            callback.onResult(moviesWithGenres)
        }, { throwable -> }
        ))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Movie>) {
    }
}