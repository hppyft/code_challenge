package com.arctouch.codechallenge.presenter

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.model.api.TmdbApi
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.data.MoviesDataSourceFactory
import com.arctouch.codechallenge.view.SearchView
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class SearchPresenterImpl(val view: SearchView): SearchPresenter{
    private var api: TmdbApi = Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi::class.java)
    private val movieList: LiveData<PagedList<Movie>>
    private val compositeDisposable = CompositeDisposable()
    private var sourceFactory: MoviesDataSourceFactory

    init {
        sourceFactory = MoviesDataSourceFactory(compositeDisposable, api, MoviesDataSourceFactory.SEARCHED_MOVIES)
        val config = PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build()
        movieList = LivePagedListBuilder<Long, Movie>(sourceFactory, config).build()
    }

    override fun onQueryChanged(query:String){
        sourceFactory.query = query
        sourceFactory.invalidate()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun getList(): LiveData<PagedList<Movie>> {
        return movieList
    }
}