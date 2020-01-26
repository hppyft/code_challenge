package com.arctouch.codechallenge.presenter

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.MoviesApplication
import com.arctouch.codechallenge.model.api.TmdbApi
import com.arctouch.codechallenge.model.data.Cache
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.data.MoviesDataSourceFactory
import com.arctouch.codechallenge.view.MovieListBaseView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class MovieListBasePresenter(dataSourceType: Int, view: MovieListBaseView) {
    private val compositeDisposable = CompositeDisposable()
    protected lateinit var sourceFactory: MoviesDataSourceFactory

    init {
        MoviesApplication.getInstance().getApi().genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Cache.cacheGenres(it.genres)
                    sourceFactory = MoviesDataSourceFactory(compositeDisposable, MoviesApplication.getInstance().getApi(), dataSourceType)
                    val config = PagedList.Config.Builder()
                            .setPageSize(20)
                            .setEnablePlaceholders(false)
                            .build()
                    view.setList(LivePagedListBuilder<Long, Movie>(sourceFactory, config).build())
                }
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}