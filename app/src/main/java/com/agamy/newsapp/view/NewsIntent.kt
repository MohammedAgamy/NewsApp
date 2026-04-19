package com.agamy.newsapp.view

sealed class NewsIntent {
    object LoadNews: NewsIntent()
    object RefreshNews : NewsIntent()
}