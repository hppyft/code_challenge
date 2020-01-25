package com.arctouch.codechallenge.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arctouch.codechallenge.HomePresenter
import com.arctouch.codechallenge.HomePresenterImpl
import com.arctouch.codechallenge.HomeView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeView {
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
        recyclerView.adapter = HomeAdapter(moviesWithGenres)
        progressBar.visibility = View.GONE
    }
}
