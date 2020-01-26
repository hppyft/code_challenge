package com.arctouch.codechallenge

import android.app.Application
import com.arctouch.codechallenge.model.api.TmdbApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MoviesApplication : Application() {

    private var api: TmdbApi? = null

    companion object {
        private lateinit var INSTANCE: MoviesApplication

        fun getInstance(): MoviesApplication {
            return INSTANCE
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    fun getApi(): TmdbApi {
        if (api == null) {
            startApi()
        }
        return api!!
    }

    private fun startApi() {
        api = Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi::class.java)
    }
}