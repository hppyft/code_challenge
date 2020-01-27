package com.arctouch.codechallenge.model.api

import com.arctouch.codechallenge.model.data.GenreResponse
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.data.MoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "pt-BR"
        const val DEFAULT_REGION = "BR"
    }

    @GET("genre/movie/list")
    fun genres(
            @Query("api_key") apiKey: String = API_KEY,
            @Query("language") language: String = DEFAULT_LANGUAGE
    ): Observable<GenreResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(
            @Query("api_key") apiKey: String = API_KEY,
            @Query("language") language: String = DEFAULT_LANGUAGE,
            @Query("page") page: Long,
            @Query("region") region: String = DEFAULT_REGION
    ): Observable<MoviesResponse>

    @GET("movie/{id}")
    fun movie(
            @Path("id") id: Long,
            @Query("api_key") apiKey: String = API_KEY,
            @Query("language") language: String = DEFAULT_LANGUAGE
    ): Observable<Movie>

    @GET("search/movie")
    fun searchMovies(
            @Query("api_key") apiKey: String = API_KEY,
            @Query("language") language: String = DEFAULT_LANGUAGE,
            @Query("query") query: String,
            @Query("page") page: Long,
            @Query("region") region: String = DEFAULT_REGION
    ): Observable<MoviesResponse>
}
