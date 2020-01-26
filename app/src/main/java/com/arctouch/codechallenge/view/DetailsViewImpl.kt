package com.arctouch.codechallenge.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.presenter.DetailsPresenter
import com.arctouch.codechallenge.presenter.DetailsPresenterImpl
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.details_activity.*

class DetailsViewImpl : AppCompatActivity(), DetailsView {
    private val presenter: DetailsPresenter

    init {
        presenter = DetailsPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val movieId = intent.getLongExtra(DetailsView.MOVIE_ID, -1)
        presenter.onCreate(movieId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
            }
        }
        return true
    }

    @SuppressLint("CheckResult")
    override fun showMovie(movie: Movie) {
        val movieImageUrlBuilder = MovieImageUrlBuilder()

        movie_title.text = if (movie.title.isNullOrEmpty()) getString(R.string.no_title_found) else movie.title

        if (!movie.posterPath.isNullOrEmpty()) {
            Glide.with(movie_poster)
                    .load(movie.posterPath.let { movieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(movie_poster)
        }

        if (!movie.backdropPath.isNullOrEmpty()) {
            Glide.with(movie_backdrop)
                    .load(movie.backdropPath.let { movieImageUrlBuilder.buildBackdropUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(movie_backdrop)
        }

        movie_release_date.text = if (movie.releaseDate.isNullOrEmpty()) getString(R.string.no_release_date_found) else movie.releaseDate

        movie_genres.text = if (movie.genres.isNullOrEmpty()) getString(R.string.no_genres_found) else movie.genres.joinToString(separator = ", ") { it.name }

        movie_overview.text = if (movie.overview.isNullOrEmpty()) getString(R.string.no_overview_found) else movie.overview

        details_progress.visibility = View.GONE
        details_elements.visibility = View.VISIBLE
    }
}