package com.arctouch.codechallenge.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.presenter.HomePresenter
import com.arctouch.codechallenge.presenter.HomePresenterImpl
import com.arctouch.codechallenge.view.adapter.MoviesPagedAdapter
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeView, MovieClickedListener {
    private lateinit var presenter: HomePresenter
    private lateinit var adapter: MoviesPagedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        initAdapter()
        presenter = HomePresenterImpl(this)
        try_again_bt.setOnClickListener {
            presenter.tryAgain()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    fun onSearchClicked(menuItem: MenuItem) {
        val intent = Intent(this, SearchViewImpl::class.java)
        startActivity(intent)
    }

    private fun initAdapter() {
        adapter = MoviesPagedAdapter(this)
        home_movies_list.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setList(list: LiveData<PagedList<Movie>>) {
        list.observe(this, Observer<PagedList<Movie>> {
            home_progress_bar.visibility = View.GONE
            no_movies_group.visibility = View.GONE
            home_movies_list.visibility = View.VISIBLE
            adapter.submitList(it)
        })
    }


    override fun onMovieClicked(movieId: Long) {
        val intent = Intent(this, DetailsViewImpl::class.java).apply {
            putExtra(DetailsView.MOVIE_ID, movieId)
        }
        startActivity(intent)
    }

    override fun showNoConnection() {
        home_progress_bar.visibility = View.GONE
        no_movies_group.visibility = View.VISIBLE
    }
}
