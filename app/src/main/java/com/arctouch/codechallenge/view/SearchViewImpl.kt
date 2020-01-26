package com.arctouch.codechallenge.view

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
import com.arctouch.codechallenge.presenter.SearchPresenter
import com.arctouch.codechallenge.presenter.SearchPresenterImpl
import com.arctouch.codechallenge.view.adapter.MoviesPagedAdapter
import kotlinx.android.synthetic.main.search_activity.*

class SearchViewImpl : AppCompatActivity(), SearchView {
    private val presenter: SearchPresenter
    private lateinit var adapter: MoviesPagedAdapter

    init {
        presenter = SearchPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initAdapter()
        setupSearchBar()
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
        adapter = MoviesPagedAdapter()
        recycler_view.adapter = adapter
    }

    override fun setList(list: LiveData<PagedList<Movie>>) {
        list.observe(this, Observer<PagedList<Movie>> {
            if (it.isNotEmpty()) {
                no_movies_tv.visibility = View.GONE
                recycler_view.visibility = View.VISIBLE
            } else {
                no_movies_tv.visibility = View.VISIBLE
                recycler_view.visibility = View.GONE
            }
            adapter.submitList(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}