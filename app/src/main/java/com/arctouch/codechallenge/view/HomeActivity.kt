package com.arctouch.codechallenge.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.presenter.HomePresenter
import com.arctouch.codechallenge.presenter.HomePresenterImpl
import com.arctouch.codechallenge.view.adapter.HomeAdapter
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeView, MovieClickedListener {
    private val presenter: HomePresenter

    init {
        presenter = HomePresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        presenter.onCreate()
    }

    override fun showMovies(moviesWithGenres: List<Movie>) {
        recyclerView.adapter = HomeAdapter(moviesWithGenres, this)
        progressBar.visibility = View.GONE
    }

    override fun onMovieClicked(movieId: Long) {
        val intent = Intent(this, DetailsViewImpl::class.java).apply {
            putExtra(DetailsView.MOVIE_ID, movieId)
        }
        startActivity(intent)
    }
}
