package com.arctouch.codechallenge.presenter

import android.annotation.SuppressLint
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.MoviesApplication
import com.arctouch.codechallenge.model.data.Cache
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.data.MoviesDataSourceFactory
import com.arctouch.codechallenge.util.NetworkUtil
import com.arctouch.codechallenge.view.HomeView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenterImpl(val view: HomeView) : HomePresenter {
    private val compositeDisposable = CompositeDisposable()
    private var sourceFactory: MoviesDataSourceFactory? = null

    init {
        tryToGetMovies()
    }

    fun tryToGetMovies(query: String = "") {
        if (NetworkUtil.isDeviceConnected()) {
            getMovies(query)
        } else {
            view.showNoMovies()
        }
    }

    @SuppressLint("CheckResult")
    private fun getMovies(query: String) {
        MoviesApplication.getInstance().getApi().genres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Cache.cacheGenres(it.genres)
                    sourceFactory = MoviesDataSourceFactory(compositeDisposable, MoviesApplication.getInstance().getApi(), query)
                    val config = PagedList.Config.Builder()
                            .setPageSize(20)
                            .setEnablePlaceholders(false)
                            .build()
                    view.setList(LivePagedListBuilder<Long, Movie>(sourceFactory!!, config).build())
                }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun tryAgain(query: String) {
        tryToGetMovies(query)
    }

    override fun onQueryChanged(query: String) {
        if (NetworkUtil.isDeviceConnected()) {
            if (sourceFactory == null) {
                tryToGetMovies(query)
            } else {
                sourceFactory?.query = query
                sourceFactory?.invalidate()
            }
        } else {
            view.showNoMovies()
        }
    }
}