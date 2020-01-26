package com.arctouch.codechallenge.presenter

interface SearchPresenter {
    fun onDestroy()
    fun onQueryChanged(query: String)
}