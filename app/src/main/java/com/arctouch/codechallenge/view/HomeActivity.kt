package com.arctouch.codechallenge.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.presenter.HomePresenter
import com.arctouch.codechallenge.presenter.HomePresenterImpl
import com.arctouch.codechallenge.view.adapter.MoviesPagedAdapter
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeView, MovieClickedListener {
    private val presenter: HomePresenter
    private lateinit var adapter: MoviesPagedAdapter

    init {
        presenter = HomePresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        initAdapter()
    }

    private fun initAdapter() {
        adapter = MoviesPagedAdapter(this)
        recyclerView.adapter = adapter
        presenter.getList().observe(this, Observer<PagedList<Movie>> {
            adapter.submitList(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


    override fun onMovieClicked(movieId: Long) {
        val intent = Intent(this, DetailsViewImpl::class.java).apply {
            putExtra(DetailsView.MOVIE_ID, movieId)
        }
        startActivity(intent)
    }
}
