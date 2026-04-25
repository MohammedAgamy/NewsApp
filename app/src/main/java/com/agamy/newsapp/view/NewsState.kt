package com.agamy.newsapp.view

import com.agamy.newsapp.data.model.NewsModel

sealed class NewsState {
    object Idle : NewsState()
    object Loading : NewsState()
    object Empty : NewsState()
    data class Success(val news: NewsModel) : NewsState()
    data class Error(val message: String) : NewsState()

}