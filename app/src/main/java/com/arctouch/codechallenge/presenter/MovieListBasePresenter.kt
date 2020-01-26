package com.arctouch.codechallenge.presenter

import android.annotation.SuppressLint
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.MoviesApplication
import com.arctouch.codechallenge.model.data.Cache
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.data.MoviesDataSourceFactory
import com.arctouch.codechallenge.util.NetworkUtil
import com.arctouch.codechallenge.view.MovieListBaseView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class MovieListBasePresenter(private val dataSourceType: Int, val view: MovieListBaseView) {
    private val compositeDisposable = CompositeDisposable()
    protected var sourceFactory: MoviesDataSourceFactory? = null

    init {
        tryToGetMovies()
    }

    fun tryToGetMovies(query: String = "") {
        if (NetworkUtil.isDeviceConnected()) {
            getMovies(query)
        } else {
            view.showNoConnection()
        }
    }

    @SuppressLint("CheckResult")
    private fun getMovies(query: String) {
        MoviesApplication.getInstance().getApi().genres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Cache.cacheGenres(it.genres)
                    sourceFactory = MoviesDataSourceFactory(compositeDisposable, MoviesApplication.getInstance().getApi(), dataSourceType, query)
                    val config = PagedList.Config.Builder()
                            .setPageSize(20)
                            .setEnablePlaceholders(false)
                            .build()
                    view.setList(LivePagedListBuilder<Long, Movie>(sourceFactory!!, config).build())
                }
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}