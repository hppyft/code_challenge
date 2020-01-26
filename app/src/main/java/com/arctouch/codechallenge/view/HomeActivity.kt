package com.arctouch.codechallenge.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        setupSearchBar()
        img_bt.setOnClickListener {
            search_view.setText("")
        }
        try_again_bt.setOnClickListener {
            presenter.tryAgain(search_view.text.toString())
        }
    }

    private fun setupSearchBar() {
        search_view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.onQueryChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
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
            if (it.isEmpty()) {
                showNoMovies()
            } else {
                home_progress_bar.visibility = View.GONE
                no_movies_group.visibility = View.GONE
                home_movies_list.visibility = View.VISIBLE
            }
            adapter.submitList(it)
        })
    }


    override fun onMovieClicked(movieId: Long) {
        val intent = Intent(this, DetailsViewImpl::class.java).apply {
            putExtra(DetailsView.MOVIE_ID, movieId)
        }
        startActivity(intent)
    }

    override fun showNoMovies() {
        home_progress_bar.visibility = View.GONE
        home_movies_list.visibility = View.GONE
        no_movies_group.visibility = View.VISIBLE
    }
}
