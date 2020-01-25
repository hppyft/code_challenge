package com.arctouch.codechallenge.presenter

import android.annotation.SuppressLint
import com.arctouch.codechallenge.model.api.TmdbApi
import com.arctouch.codechallenge.view.DetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class DetailsPresenterImpl(val view: DetailsView) : DetailsPresenter {

    private lateinit var api: TmdbApi

    override fun onCreate(movieId: Long) {
        api = Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi::class.java)

        getMovies(movieId)
    }

    @SuppressLint("CheckResult")
    private fun getMovies(movieId: Long) {
        api.movie(movieId, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.showMovie(it)
                }
    }
}