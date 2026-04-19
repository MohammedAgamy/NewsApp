package com.agamy.newsapp.view

import com.agamy.newsapp.data.model.NewsModel

sealed class NewsState {
    object IdIl : NewsState()
    object Loading : NewsState()
    data class Success(val news: List<NewsModel>): NewsState()
    data class Error(val message: String): NewsState()

}