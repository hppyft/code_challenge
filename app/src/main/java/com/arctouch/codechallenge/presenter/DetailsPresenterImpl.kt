package com.arctouch.codechallenge.presenter

import android.annotation.SuppressLint
import com.arctouch.codechallenge.MoviesApplication
import com.arctouch.codechallenge.model.api.MoviesModel
import com.arctouch.codechallenge.view.DetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsPresenterImpl(val view: DetailsView) : DetailsPresenter {

    override fun onCreate(movieId: Long) {
        getMovies(movieId)
    }

    @SuppressLint("CheckResult")
    private fun getMovies(movieId: Long) {
        MoviesApplication.getInstance().getApi().movie(movieId, MoviesModel.API_KEY, MoviesModel.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.showMovie(it)
                }
    }
}