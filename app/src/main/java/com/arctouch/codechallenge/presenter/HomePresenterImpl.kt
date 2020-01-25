package com.arctouch.codechallenge.presenter

import android.annotation.SuppressLint
import com.arctouch.codechallenge.model.api.TmdbApi
import com.arctouch.codechallenge.model.data.Cache
import com.arctouch.codechallenge.view.HomeView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class HomePresenterImpl(val view: HomeView) : HomePresenter {
    private lateinit var api: TmdbApi

    override fun onCreate() {
        api = Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi::class.java)
        findGenres()
    }

    @SuppressLint("CheckResult")
    fun startGettingMovies() {
        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val moviesWithGenres = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    view.showMovies(moviesWithGenres)
                }
    }

    @SuppressLint("CheckResult")
    fun findGenres() {
        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Cache.cacheGenres(it.genres)
                    startGettingMovies()
                }
    }
}