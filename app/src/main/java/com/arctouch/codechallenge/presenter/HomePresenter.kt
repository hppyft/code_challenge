package com.arctouch.codechallenge.presenter

interface HomePresenter {
    fun onDestroy()
    fun onQueryChanged(query: String)
    fun tryAgain(query: String)
}