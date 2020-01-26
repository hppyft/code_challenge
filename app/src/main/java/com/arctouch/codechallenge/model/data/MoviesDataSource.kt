package com.arctouch.codechallenge.model.data

import androidx.paging.ItemKeyedDataSource
import com.arctouch.codechallenge.model.api.TmdbApi
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSource(private val compositeDisposable: CompositeDisposable, val method: (Long) -> Observable<MoviesResponse>) : ItemKeyedDataSource<Long, Movie>() {

    var page = 1L

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Movie>) {
        compositeDisposable.add(method(page++).subscribe({ movies ->
            val moviesWithGenres = movies.results.map { movie ->
                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
            }
            callback.onResult(moviesWithGenres)
        }, { throwable -> }
        ))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Movie>) {
        compositeDisposable.add(method(page++).subscribe({ movies ->
            val moviesWithGenres = movies.results.map { movie ->
                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
            }
            callback.onResult(moviesWithGenres)
        }, { throwable -> }
        ))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Movie>) {
    }

    override fun getKey(item: Movie): Long {
        return page
    }
}